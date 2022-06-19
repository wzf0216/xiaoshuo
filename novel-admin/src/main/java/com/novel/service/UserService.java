package com.novel.service;

import com.novel.entity.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户
 */
public interface UserService {
    User get(Long id);

    List<User> list();
    List<User> search(String username);
    int count(Map<String, Object> map);

    int save(User user);

    int update(User user);

    int remove(Long id);

    int batchRemove(Long[] ids);

    Map<Object, Object> tableSta(Date minDate);
}
