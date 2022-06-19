package com.novel.service;

import com.novel.entity.BookCategory;


import java.util.List;
import java.util.Map;

public interface  BookCategoryService {
  BookCategory get(Integer id);

    List<BookCategory> list();

    long count();

    int save(BookCategory category);

    int update(BookCategory category);

    int remove(Integer id);

    int batchRemove(Integer[] ids);
}
