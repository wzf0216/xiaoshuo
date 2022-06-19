package com.novel.service;

import com.novel.entity.Author;
import com.novel.entity.User;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

/**
 * 作者
 */
public interface AuthorService {
    Author get(Long id);

    List<Author> list();
    List<Author> search(String penname);
    int count(Map<String, Object> map);

    int save(Author author);

    int update(Author author);

    int remove(Long id);

    int batchRemove(Long[] ids);

    Map<Object, Object> tableSta(Data minData);
}
