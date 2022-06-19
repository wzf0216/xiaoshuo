package com.novel.service;




import com.novel.entity.News;
import com.novel.vo.NewsVO;

import java.util.List;


public interface NewsService {
    /**
     * 查询首页新闻
     * @return
     * */
    List<News> listIndexNews();

    /**
     * 查询新闻
     * @param newsId 新闻id
     * @return 新闻
     * */
    News queryNewsInfo(Long newsId);

    /**
     * 分页查询新闻列表
     * @param page 页码
     * @param pageSize 分页大小
     * @return 新闻集合
     * */
    List<NewsVO> listByPage(int page, int pageSize);

}
