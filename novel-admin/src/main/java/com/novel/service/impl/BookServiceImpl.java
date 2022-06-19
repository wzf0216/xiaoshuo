package com.novel.service.impl;

import com.novel.entity.Book;
import com.novel.mapper.BookMapper;
import com.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;


import javax.xml.crypto.Data;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;


    @Override
    public Book get(Long id) {
        return bookMapper.get(id);
    }

    @Override
    public List<Book> list() {
        return bookMapper.list();
    }

    @Override
    public List<Book> search(String bookname) {
        return bookMapper.search(bookname);
    }

    @Override
    public int save(Book book) {
        return bookMapper.save(book);
    }

    @Override
    public int count(Map<String, Object> map) {
        return bookMapper.count(map);
    }

    @Override
    public int update(Book book) {
        return bookMapper.update(book);
    }

    @Override
    public int remove(Long id) {
        return bookMapper.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return bookMapper.batchRemove(ids);
    }

    @Override
    public Map<Object, Object> tableSta(Data minDate) {
        return null;
    }
}
