package com.novel.service.impl;

import com.github.pagehelper.PageHelper;
import com.novel.entity.BookCategory;
import com.novel.mapper.BookCategoryDynamicSqlSupport;
import com.novel.mapper.BookCategoryMapper;
import com.novel.service.BookCategoryService;
import lombok.RequiredArgsConstructor;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.novel.mapper.BookCategoryDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {
    private final BookCategoryMapper bookCategoryMapper;

    @Override
    public BookCategory get(Integer id) {
        SelectStatementProvider selectStatement = select(BookCategoryDynamicSqlSupport.id, BookCategoryDynamicSqlSupport.workDirection, name, sort, createUserId, createTime, updateUserId, updateTime)
                .from(bookCategory)
                .where(BookCategoryDynamicSqlSupport.id, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);


        return bookCategoryMapper.selectMany(selectStatement).get(0);

    }

    @Override
    public List<BookCategory> list() {

        SelectStatementProvider selectStatement = select(BookCategoryDynamicSqlSupport.id, BookCategoryDynamicSqlSupport.workDirection, name, sort, createUserId, createTime, updateUserId, updateTime)
                .from(bookCategory)
                .orderBy(createTime.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return bookCategoryMapper.selectMany(selectStatement);
    }

    @Override
    public long count() {
        SelectStatementProvider selectStatement = select(SqlBuilder.count())
                .from(bookCategory)
                .where(id, isNotNull())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return bookCategoryMapper.count(selectStatement);
    }

    @Override
    public int save(BookCategory category) {
        BookCategory bookCategory = new BookCategory();
        bookCategory.setId(category.getId());
        bookCategory.setWorkDirection(category.getWorkDirection());
        bookCategory.setName(category.getName());
        bookCategory.setSort(category.getSort());
        bookCategory.setCreateUserId(category.getCreateUserId());
        bookCategory.setCreateTime(category.getCreateTime());
        bookCategory.setUpdateUserId(category.getUpdateUserId());
        bookCategory.setUpdateTime(category.getUpdateTime());
        return bookCategoryMapper.insert(bookCategory);
    }

    @Override
    public int update(BookCategory category) {
        return bookCategoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int remove(Integer id) {
        return bookCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        int num = 0;
        for (int i = 0; i < ids.length; i++) {
            bookCategoryMapper.deleteByPrimaryKey(ids[i]);
            num++;

        }
        return num;
    }
}
