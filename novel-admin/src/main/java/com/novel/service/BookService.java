package com.novel.service;

import com.novel.entity.Book;
import com.novel.entity.User;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

/**
 * 小说
 */
public interface BookService {
    Book get(Long id);

    List<Book> list();
    List<Book> search(String username);
    int save(Book book);
    int count(Map<String, Object> map);
    int update(Book book);

    int remove(Long id);

    int batchRemove(Long[] ids);

    Map<Object, Object> tableSta(Data minDate);
}
