package com.novel.service.impl;

import com.github.pagehelper.PageHelper;
import com.novel.entity.User;



import com.novel.mapper.UserMapper;
import com.novel.service.UserService;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;



import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;


    @Override
    public User get(Long id) {
        return userMapper.get(id);
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public List<User> search(String userName) {
        return userMapper.search(userName);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userMapper.count(map);
    }



    @Override
    public int save(User user) {
        return userMapper.save(user);
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int remove(Long id) {
        return userMapper.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return userMapper.batchRemove(ids);
    }

    @Override
    public Map<Object, Object> tableSta(Date minDate) {
        return null;
    }
}
