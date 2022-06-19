package com.novel.mapper;

import com.novel.entity.Author;
import com.novel.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    User get(Long id);
     List<User> search(String username);
    List<User> list();

    int  count(Map<String,Object> map);

    int save(User user);

    int update(User user);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<Map<Object, Object>> tableSta(Date minDate);
}
