package com.novel.mapper;


import com.novel.vo.BookShelfVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface FrontUserBookshelfMapper extends UserBookshelfMapper {
//用户书架
    List<BookShelfVO> listBookShelf(@Param("userId") Long userId);

}
