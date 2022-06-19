package com.novel.mapper;


import com.novel.vo.BookReadHistoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface FrontUserReadHistoryMapper extends UserReadHistoryMapper {
    //用户阅读记录
    List<BookReadHistoryVO> listReadHistory(@Param("userId") Long userId);

}
