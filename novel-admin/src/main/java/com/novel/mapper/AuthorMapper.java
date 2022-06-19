package com.novel.mapper;

import com.novel.entity.Author;
import org.apache.ibatis.annotations.Mapper;

;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface AuthorMapper {
    Author get(Long id);

    List<Author> list();
    List<Author> search(String nickname);

    int count(Map<String,Object> map);


    int save(Author author);

    int update(Author author);

    int remove(Long id);

    int batchRemove(Long[] ids);

    List<Map<Object, Object>> tableSta(Date minDate);
}
