package com.novel.service;

import com.novel.entity.News;

import java.util.List;
import java.util.Map;

/**
 * 新闻
 */
public interface NewsService {
    News get(Long id);

    List<News> list();

    long count();

    int save(News news);

    int update(News news);

    int remove(Long id);

    int batchRemove(Long[] ids);
}
