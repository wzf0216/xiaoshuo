package com.novel.service.impl;

import com.novel.entity.UserFeedback;
import com.novel.mapper.OrderPayDynamicSqlSupport;
import com.novel.mapper.UserFeedbackDynamicSqlSupport;
import com.novel.mapper.UserFeedbackMapper;
import com.novel.service.UserFeedbackService;
import lombok.RequiredArgsConstructor;

import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.novel.mapper.BookCategoryDynamicSqlSupport.bookCategory;
import static com.novel.mapper.BookCategoryDynamicSqlSupport.id;
import static com.novel.mapper.SysUserDynamicSqlSupport.userId;
import static com.novel.mapper.UserFeedbackDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Service
@RequiredArgsConstructor
public class UserFeedbackServiceImpl implements UserFeedbackService {
    private final UserFeedbackMapper userFeedbackMapper;
    @Override
    public UserFeedback get(Long id) {
        SelectStatementProvider selectStatement= select(UserFeedbackDynamicSqlSupport.id,userId,content,createTime)
                .from(userFeedback)
                .where(UserFeedbackDynamicSqlSupport.userId,isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return userFeedbackMapper.selectMany(selectStatement).get(0);

    }

    @Override
    public List<UserFeedback> list() {
        SelectStatementProvider selectStatement= select(UserFeedbackDynamicSqlSupport.id,userId,content,createTime)
                .from(userFeedback)
                .orderBy(createTime.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return userFeedbackMapper.selectMany(selectStatement);
    }

    @Override
    public long count() {
        SelectStatementProvider selectStatement = select(SqlBuilder.count())
                .from(userFeedback)
                .where(id, isNotNull())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return userFeedbackMapper.count(selectStatement);
    }

    @Override
    public int save(UserFeedback user) {
        return userFeedbackMapper.insert(user);
    }

    @Override
    public int update(UserFeedback user) {
        return userFeedbackMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int remove(Long id) {
        return userFeedbackMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        int num = 0;
        for (int i = 0; i < ids.length; i++) {
            userFeedbackMapper.deleteByPrimaryKey(ids[i]);
            num++;

        }
        return num;
    }
}
