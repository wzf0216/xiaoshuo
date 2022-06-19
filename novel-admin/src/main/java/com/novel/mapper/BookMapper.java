package com.novel.mapper;

import com.novel.entity.Author;
import com.novel.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

@Mapper
public interface BookMapper {
    Book get(Long id);
    List<Book> search(String bookname);
    int count(Map<String,Object> map);
    List<Book> list();

    int save(Book book);

    int update(Book book);

    int remove(Long id);

    int batchRemove(Long[] ids);

    Map<Object, Object> tableSta(Data minDate);
}