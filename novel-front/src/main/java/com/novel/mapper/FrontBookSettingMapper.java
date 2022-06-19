package com.novel.mapper;


import com.novel.vo.BookSettingVO;

import java.util.List;


public interface FrontBookSettingMapper extends BookSettingMapper {
//前台首页小说设置
    List<BookSettingVO> listVO();
}
