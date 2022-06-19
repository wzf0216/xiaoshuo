package com.novel.service.impl;

import com.github.pagehelper.PageHelper;
import com.novel.entity.Author;

import com.novel.entity.User;
import com.novel.mapper.AuthorMapper;
import com.novel.service.AuthorService;
import javafx.scene.input.DataFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorMapper authorMapper;

    @Override
    public Author get(Long id) {
        return authorMapper.get(id);
    }

    @Override
    public List<Author> list() {
        return authorMapper.list();
    }

    @Override
    public List<Author> search(String penname) {
        return authorMapper.search(penname);
    }

    @Override
    public int count(Map<String, Object> map) {
        return authorMapper.count(map);
    }



    @Override
    public int save(Author author) {
        return authorMapper.save(author);
    }

    @Override
    public int update(Author author) {
        return authorMapper.update(author);
    }

    @Override
    public int remove(Long id) {
        return authorMapper.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return authorMapper.batchRemove(ids);
    }

    @Override
    public Map<Object, Object> tableSta(Data minData) {
        return null;
    }
}
