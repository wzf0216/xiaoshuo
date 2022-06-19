package com.novel.service.impl;

import com.github.pagehelper.PageHelper;
import com.novel.entity.News;
import com.novel.mapper.NewsDynamicSqlSupport;
import com.novel.mapper.NewsMapper;
import com.novel.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;
import static com.novel.mapper.NewsDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private  final NewsMapper newsMapper;
    @Override
    public News get(Long id) {
        SelectStatementProvider selectStatement =select(NewsDynamicSqlSupport.id,catId,catName,sourceName,title,content,createTime,createUserId,updateTime,updateUserId)
                .from(news)
                .where(NewsDynamicSqlSupport.id,isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return newsMapper.selectMany(selectStatement).get(0);
    }

    @Override
    public List<News> list() {

        SelectStatementProvider selectStatement =select(NewsDynamicSqlSupport.id,catId,catName,sourceName,title,content,createTime,createUserId,updateTime,updateUserId)
                .from(news)
                .where(NewsDynamicSqlSupport.id,isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return newsMapper.selectMany(selectStatement);
    }

    @Override
    public long count() {
        SelectStatementProvider selectStatement=select(SqlBuilder.count())
                .from(news)
                .where(id,isNotNull())
                .orderBy(createTime.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return newsMapper.count(selectStatement);
    }

    @Override
    public int save(News news) {

        return newsMapper.insertSelective(news);
    }

    @Override
    public int update(News news) {
        return newsMapper.updateByPrimaryKeySelective(news);
    }

    @Override
    public int remove(Long id) {
        return newsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        int num=0;
        for (int i = 0; i <ids.length ; i++) {
            newsMapper.deleteByPrimaryKey(ids[i]);
            num++;

        }
        return num;
    }
}
