package com.novel.mapper;


import com.novel.entity.Book;
import com.novel.search.BookSP;
import com.novel.vo.BookVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;



public interface FrontBookMapper extends BookMapper {

//搜索
    List<BookVO> searchByPage(BookSP params);
//增加点击量
    void addVisitCount(@Param("bookId") Long bookId);
//根据分类查找书籍
    List<Book> listRecBookByCatId(@Param("catId") Integer catId);
//增加评论数
    void addCommentCount(@Param("bookId") Long bookId);
//获得小说封面
    List<Book> queryNetworkPicBooks(@Param("localPicPrefix") String localPicPrefix, @Param("limit") Integer limit);

    /**
     * 按评分随机查询小说集合
     * @param limit 查询条数
     * @return 小说集合
     * */
    List<Book> selectIdsByScoreAndRandom(@Param("limit") int limit);
}
