package com.novel.service.impl;

import com.novel.entity.AuthorCode;
import com.novel.mapper.AuthorCodeMapper;
import com.novel.service.AuthorCodeService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorCodeServiceImpl implements AuthorCodeService {
    private final AuthorCodeMapper authorCodeMapper;


    @Override
    public AuthorCode get(Long id) {
        return authorCodeMapper.get(id);
    }

    @Override
    public List<AuthorCode> search(String code) {
        return authorCodeMapper.search(code);
    }

    @Override
    public List<AuthorCode> list() {
        return authorCodeMapper.list();
    }

    @Override
    public long count() {
        return authorCodeMapper.count();
    }

    @Override
    public int save(AuthorCode authorCode) {
        return authorCodeMapper.save(authorCode);
    }

    @Override
    public int update(AuthorCode authorCode) {
        return authorCodeMapper.update(authorCode);
    }

    @Override
    public int remove(Long id) {
        return authorCodeMapper.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return authorCodeMapper.batchRemove(ids);
    }
}
