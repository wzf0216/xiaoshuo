package com.novel.service.impl;


import com.novel.entity.Author;
import com.novel.mapper.AuthorCodeDynamicSqlSupport;
import com.novel.mapper.AuthorCodeMapper;
import com.novel.mapper.AuthorDynamicSqlSupport;
import com.novel.mapper.AuthorMapper;
import com.novel.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static org.mybatis.dynamic.sql.select.SelectDSL.select;


@Service
//构造器注入代替@Autowired
@RequiredArgsConstructor

public class AuthorServiceImpl implements AuthorService {

    private  final AuthorMapper authorMapper;

    private final AuthorCodeMapper authorCodeMapper;

    /**
     * 校验笔名是否存在
     * @param penName 校验的笔名
     * @return true：存在该笔名，false: 不存在该笔名
     * */
    @Override
    public Boolean checkPenName(String penName) {
        return authorMapper.count(c ->
                c.where(AuthorDynamicSqlSupport.penName, isEqualTo(penName))) > 0;
    }
    /**
     * 作家注册
     * @param userId 注册用户ID
     *@param author 注册信息
     * @return 返回错误信息
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String register(Long userId, Author author) {
        Date currentDate = new Date();
        //判断邀请码是否有效
        if (authorCodeMapper.count(c ->
                c.where(AuthorCodeDynamicSqlSupport.inviteCode, isEqualTo(author.getInviteCode()))
                        .and(AuthorCodeDynamicSqlSupport.isUse, isEqualTo((byte) 0))
                        .and(AuthorCodeDynamicSqlSupport.validityTime, isGreaterThan(currentDate))) > 0) {
             //邀请码有效
            //保存作家信息

            author.setUserId(userId);
            author.setCreateTime(currentDate);
            authorMapper.insertSelective(author);
            //设置邀请码状态为已使用
            authorCodeMapper.update(update(AuthorCodeDynamicSqlSupport.authorCode)
                    .set(AuthorCodeDynamicSqlSupport.isUse)
                    .equalTo((byte) 1)
                    .where(AuthorCodeDynamicSqlSupport.inviteCode,isEqualTo(author.getInviteCode()))
                    .build()
                    .render(RenderingStrategies.MYBATIS3));
            return "";
        } else {
            //邀请码无效
            return "邀请码无效！";
        }

    }
    /**
     * 判断是否是作家
     * @param userId 用户ID
     * @return true：是作家，false: 不是作家
     * */
    @Override
    public Boolean isAuthor(Long userId) {
        return authorMapper.count(c ->
                c.where(AuthorDynamicSqlSupport.userId, isEqualTo(userId))) > 0;
    }
    /**
     * 查询作家信息
     * @param userId 用户ID
     * @return 作家对象
     * */
    @Override
    public Author queryAuthor(Long userId) {
        return authorMapper.selectMany(
        select(AuthorDynamicSqlSupport.id,AuthorDynamicSqlSupport.penName,AuthorDynamicSqlSupport.status)
        .from(AuthorDynamicSqlSupport.author)
        .where(AuthorDynamicSqlSupport.userId,isEqualTo(userId))
                .build()
                .render(RenderingStrategies.MYBATIS3)).get(0);
    }
}
