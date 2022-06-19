package com.novel.mapper;

import com.novel.entity.Author;
import com.novel.entity.AuthorCode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorCodeMapper {
    //根据id得到邀请码
    AuthorCode get(Long id);
    List<AuthorCode> search(String code);
    //邀请码列表
    List<AuthorCode> list();
    long count();
    //新建邀请码
    int save(AuthorCode authorCode);
    //更新
    int update(AuthorCode authorCode);
    //删除
    int remove(Long id);
    //批量删除
    int batchRemove(Long[] ids);
}