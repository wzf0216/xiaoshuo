package com.novel.service;

import com.novel.entity.AuthorCode;
import com.novel.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 作者邀请码
 */
public interface AuthorCodeService {
    //根据id得到邀请码
  AuthorCode get(Long id);
  //模糊查询
  List<AuthorCode> search(String username);
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
