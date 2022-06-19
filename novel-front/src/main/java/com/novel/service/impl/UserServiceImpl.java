package com.novel.service.impl;

import com.github.pagehelper.PageHelper;

import com.novel.core.bean.UserDetails;
import com.novel.core.enums.ResponseStatus;
import com.novel.core.exception.BusinessException;
import com.novel.core.utils.BeanUtil;
import com.novel.core.utils.IdWorker;
import com.novel.core.utils.MD5Util;

import com.novel.entity.*;
import com.novel.entity.User;
import com.novel.form.UserForm;
import com.novel.mapper.FrontUserBookshelfMapper;
import com.novel.mapper.FrontUserReadHistoryMapper;
import com.novel.service.UserService;
import com.novel.vo.BookReadHistoryVO;
import com.novel.vo.BookShelfVO;
import com.novel.vo.UserFeedbackVO;
import com.novel.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.novel.mapper.UserBookshelfDynamicSqlSupport.userBookshelf;
import static com.novel.mapper.UserDynamicSqlSupport.*;

import java.util.Date;
import java.util.List;


import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static com.novel.mapper.UserFeedbackDynamicSqlSupport.userFeedback;
import static com.novel.mapper.UserReadHistoryDynamicSqlSupport.userReadHistory;
import static org.mybatis.dynamic.sql.select.SelectDSL.select;


@Service
@Slf4j
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final FrontUserBookshelfMapper userBookshelfMapper;

    private final FrontUserReadHistoryMapper userReadHistoryMapper;

    private final UserFeedbackMapper userFeedbackMapper;

    private final UserBuyRecordMapper userBuyRecordMapper;

    /**
     * 用户注册
     * @param form 用户注册提交信息类
     * @return jwt载体信息类
     * */
    @Override
    public UserDetails register(UserForm form) {
        //查询用户名是否已注册
        SelectStatementProvider selectStatement = select(count(id))
                .from(user)
                .where(username, isEqualTo(form.getUsername()))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        long count = userMapper.count(selectStatement);
        if (count > 0) {
            //用户名已注册
            throw new BusinessException(ResponseStatus.USERNAME_EXIST);
        }
        User entity = new User();
        BeanUtils.copyProperties(form, entity);
        //数据库生成注册记录
        Long id = new IdWorker().nextId();
        entity.setId(id);
        entity.setNickName(entity.getUsername());
        Date currentDate = new Date();
        entity.setCreateTime(currentDate);
        entity.setUpdateTime(currentDate);
        entity.setPassword(MD5Util.MD5Encode(entity.getPassword(), Charsets.UTF_8.name()));
        userMapper.insertSelective(entity);
        //生成UserDetail对象并返回
        UserDetails userDetails = new UserDetails();
        userDetails.setId(id);
        userDetails.setUsername(entity.getUsername());
        return userDetails;
    }
    /**
     * 用户登陆
     * @param form 用户登陆提交信息类
     * @return jwt载体信息类
     * */
    @Override
    public UserDetails login(UserForm form) {
        //根据用户名密码查询记录
        SelectStatementProvider selectStatement = select(id, username,status)
                .from(user)
                .where(username, isEqualTo(form.getUsername()))
                .and(password, isEqualTo(MD5Util.MD5Encode(form.getPassword(), Charsets.UTF_8.name())))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        List<User> users = userMapper.selectMany(selectStatement);
        if (users.size() == 0) {
            throw new BusinessException(ResponseStatus.USERNAME_PASS_ERROR);
        }

        //生成UserDetail对象并返回
        UserDetails userDetails = new UserDetails();
        userDetails.setId(users.get(0).getId());
        userDetails.setUsername(form.getUsername());
        userDetails.setStatus(users.get(0).getStatus());
        return userDetails;
    }
    /**
     * 查询小说是否已加入书架
     * @param userId 用户ID
     * @param bookId 小说ID
     * @return true:已加入书架，未加入书架
     * */
    @Override
    public Boolean queryIsInShelf(Long userId, Long bookId) {
        SelectStatementProvider selectStatement = select(count(UserBookshelfDynamicSqlSupport.id))
                .from(userBookshelf)
                .where(UserBookshelfDynamicSqlSupport.userId, isEqualTo(userId))
                .and(UserBookshelfDynamicSqlSupport.bookId, isEqualTo(bookId))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return userBookshelfMapper.count(selectStatement) > 0;
    }
    /**
     * 加入书架
     * @param userId 用户ID
     * @param bookId 小说ID
     * @param preContentId 阅读的内容ID
     * */
    @Override
    public void addToBookShelf(Long userId, Long bookId, Long preContentId) {
        if (!queryIsInShelf(userId, bookId)) {
            UserBookshelf shelf = new UserBookshelf();
            shelf.setUserId(userId);
            shelf.setBookId(bookId);
            shelf.setPreContentId(preContentId);
            shelf.setCreateTime(new Date());
            userBookshelfMapper.insert(shelf);
        }

    }
    /**
     * 移出书架
     * @param userId 用户ID
     * @param bookId 小说ID
     * */
    @Override
    public void removeFromBookShelf(Long userId, Long bookId) {
        DeleteStatementProvider deleteStatement = deleteFrom(userBookshelf)
                .where(UserBookshelfDynamicSqlSupport.userId, isEqualTo(userId))
                .and(UserBookshelfDynamicSqlSupport.bookId, isEqualTo(bookId))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        userBookshelfMapper.delete(deleteStatement);

    }
    /**
     * 查询书架
     * @param userId 用户ID
     * @param page
     * @param pageSize
     * @return 书架集合
     * */
    @Override
    public List<BookShelfVO> listBookShelfByPage(Long userId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return userBookshelfMapper.listBookShelf(userId);
    }
    /**
     * 添加阅读记录
     * @param userId 用户id
     * @param bookId 书籍id
     * @param preContentId 阅读的目录id
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addReadHistory(Long userId, Long bookId, Long preContentId) {

        Date currentDate = new Date();
        //删除该书以前的历史记录
        DeleteStatementProvider deleteStatement = deleteFrom(userReadHistory)
                .where(UserReadHistoryDynamicSqlSupport.bookId, isEqualTo(bookId))
                .and(UserReadHistoryDynamicSqlSupport.userId, isEqualTo(userId))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        userReadHistoryMapper.delete(deleteStatement);

        //插入该书新的历史记录
        UserReadHistory userReadHistory = new UserReadHistory();
        userReadHistory.setBookId(bookId);
        userReadHistory.setUserId(userId);
        userReadHistory.setPreContentId(preContentId);
        userReadHistory.setCreateTime(currentDate);
        userReadHistory.setUpdateTime(currentDate);
        userReadHistoryMapper.insertSelective(userReadHistory);


        //更新书架的阅读历史
        UpdateStatementProvider updateStatement = update(userBookshelf)
                .set(UserBookshelfDynamicSqlSupport.preContentId)
                .equalTo(preContentId)
                .set(UserBookshelfDynamicSqlSupport.updateTime)
                .equalTo(currentDate)
                .where(UserBookshelfDynamicSqlSupport.userId, isEqualTo(userId))
                .and(UserBookshelfDynamicSqlSupport.bookId, isEqualTo(bookId))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        userBookshelfMapper.update(updateStatement);


    }
    /**
     * 添加反馈
     * @param userId 用户id
     * @param content 反馈内容
     * */
    @Override
    public void addFeedBack(Long userId, String content) {
        UserFeedback feedback = new UserFeedback();
        feedback.setUserId(userId);
        feedback.setContent(content);
        feedback.setCreateTime(new Date());
        userFeedbackMapper.insertSelective(feedback);
    }
    /**
     * 分页查询我的反馈列表
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 分页大小
     * @return 反馈集合
     * */
    @Override
    public List<UserFeedbackVO> listUserFeedBackByPage(Long userId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        SelectStatementProvider selectStatement = select(UserFeedbackDynamicSqlSupport.content, UserFeedbackDynamicSqlSupport.createTime)
                .from(userFeedback)
                .where(UserFeedbackDynamicSqlSupport.userId, isEqualTo(userId))
                .orderBy(UserFeedbackDynamicSqlSupport.id.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return BeanUtil.copyList(userFeedbackMapper.selectMany(selectStatement), UserFeedbackVO.class);
    }
    /**
     * 查询个人信息
     * @param userId 用户id
     * @return 用户信息
     * */
    @Override
    public User userInfo(Long userId) {
        SelectStatementProvider selectStatement = select(username, nickName, userPhoto, userSex, accountBalance)
                .from(user)
                .where(id, isEqualTo(userId))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return userMapper.selectMany(selectStatement).get(0);
    }
    /**
     * 分页查询阅读记录
     * @param userId 用户id
     * @param page 页码
     * @param pageSize 分页大小
     * @return
     * */
    @Override
    public List<BookReadHistoryVO> listReadHistoryByPage(Long userId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return userReadHistoryMapper.listReadHistory(userId);
    }
    /**
     * 更新个人信息
     * @param userId 用户id
     * @param user 需要更新的信息
     * */
    @Override
    public void updateUserInfo(Long userId, User user) {
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setNickName(user.getNickName());
        updateUser.setUserSex(user.getUserSex());
        updateUser.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(updateUser);

    }
    /**
     * 更新密码
     * @param userId 用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * */
    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        SelectStatementProvider selectStatement = select(password)
                .from(user)
                .where(id, isEqualTo(userId))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        if (!userMapper.selectMany(selectStatement).get(0).getPassword().equals(MD5Util.MD5Encode(oldPassword, Charsets.UTF_8.name()))) {
            throw new BusinessException(ResponseStatus.OLD_PASSWORD_ERROR);
        }
        UpdateStatementProvider updateStatement = update(user)
                .set(password)
                .equalTo(MD5Util.MD5Encode(newPassword, Charsets.UTF_8.name()))
                .where(id, isEqualTo(userId))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        userMapper.update(updateStatement);

    }
    /**
     * 增加用户余额
     * @param userId 用户ID
     * @param amount 增加的余额 */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addAmount(Long userId, int amount) {
        User user = this.userInfo(userId);
        user.setId(userId);
        user.setAccountBalance(user.getAccountBalance() + amount);
        userMapper.updateByPrimaryKeySelective(user);

    }
    /**
     * 判断用户是否购买过该小说章节
     * @param userId 用户ID
     * @param bookIndexId 章节目录ID
     * @return true:购买过，false:没购买
     * */
    @Override
    public boolean queryIsBuyBookIndex(Long userId, Long bookIndexId) {

        return userBuyRecordMapper.count(c ->
                c.where(UserBuyRecordDynamicSqlSupport.userId, isEqualTo(userId))
                        .and(UserBuyRecordDynamicSqlSupport.bookIndexId, isEqualTo(bookIndexId))) > 0;
    }
    /**
     * 购买小说章节
     * @param userId 用户ID
     * @param buyRecord 购买信息
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void buyBookIndex(Long userId, UserBuyRecord buyRecord) {
        //查询用户余额
        long balance = userInfo(userId).getAccountBalance();
        if (balance < 10) {
            //余额不足
            throw new BusinessException(ResponseStatus.USER_NO_BALANCE);
        }
        buyRecord.setUserId(userId);
        buyRecord.setCreateTime(new Date());
        buyRecord.setBuyAmount(10);
        //生成购买记录
        userBuyRecordMapper.insertSelective(buyRecord);

        //减少用户余额
        userMapper.update(update(user)
                .set(UserDynamicSqlSupport.accountBalance)
                .equalTo(balance - 10)
                .where(id, isEqualTo(userId))
                .build()
                .render(RenderingStrategies.MYBATIS3));
    }


}
