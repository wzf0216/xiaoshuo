package com.novel.mapper;


import com.novel.vo.BookCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface FrontBookCommentMapper extends BookCommentMapper {
//获得评论集合
    List<BookCommentVO> listCommentByPage(@Param("userId") Long userId, @Param("bookId") Long bookId);

}
