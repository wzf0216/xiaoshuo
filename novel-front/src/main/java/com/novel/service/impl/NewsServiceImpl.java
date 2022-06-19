package com.novel.service.impl;

import com.github.pagehelper.PageHelper;

import com.novel.core.cache.CacheKey;
import com.novel.core.cache.CacheService;
import com.novel.core.utils.BeanUtil;
import com.novel.entity.News;
import com.novel.mapper.NewsMapper;
import com.novel.service.NewsService;
import com.novel.vo.NewsVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import static  com.novel.mapper.NewsDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.select.SelectDSL.select;


@Service
@RequiredArgsConstructor

public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;

    private final CacheService cacheService;


    /**
     * 查询首页新闻
     *
     * @return
     */
    @Override
    public List<News> listIndexNews() {
        List<News> result = (List<News>) cacheService.getObject(CacheKey.INDEX_NEWS_KEY);
        if(result == null || result.size() == 0) {
            SelectStatementProvider selectStatement = select(id, catName, catId, title)
                    .from(news)
                    //根据创建时间降序排序
                    .orderBy(createTime.descending())
                    .limit(2)
                    .build()
                    .render(RenderingStrategies.MYBATIS3);
            result = newsMapper.selectMany(selectStatement);
            cacheService.setObject(CacheKey.INDEX_NEWS_KEY,result);
        }
        return result;
    }

    /**
     * 查询新闻
     *
     * @param newsId 新闻id
     * @return 新闻
     */
    @Override
    public News queryNewsInfo(Long newsId) {
        SelectStatementProvider selectStatement = select(news.allColumns())
                .from(news)
                .where(id,isEqualTo(newsId))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return newsMapper.selectMany(selectStatement).get(0);
    }

    /**
     * 分页查询新闻列表
     *
     * @param page     页码
     * @param pageSize 分页大小
     * @return 新闻集合
     */
    @Override
    public List<NewsVO> listByPage(int page, int pageSize) {
        PageHelper.startPage(page,pageSize);
        SelectStatementProvider selectStatement = select(id, catName, catId, title,createTime)
                .from(news)
                .orderBy(createTime.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return BeanUtil.copyList(newsMapper.selectMany(selectStatement),NewsVO.class);
    }
}
