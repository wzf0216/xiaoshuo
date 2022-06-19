package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.core.utils.ResponseStatus;
import com.novel.core.utils.ResultBean;
import com.novel.entity.Author;
import com.novel.entity.BookCategory;
import com.novel.mapper.BookCategoryMapper;
import com.novel.service.BookCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("bookCategory")
@RequiredArgsConstructor
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;

    /**
     * 分页浏览
     *
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public ResultBean list(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum, 5);
        List<BookCategory> list = bookCategoryService.list();
        if (list == null&&list.size()==0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<BookCategory> pageInfo = new PageInfo<BookCategory>(list);
        if (pageInfo.getSize() > 0) {
            return ResultBean.ok(pageInfo);
        }
        return ResultBean.error();
    }

    /**
     * 添加
     *
     * @param authorCode
     * @return
     */
    @ResponseBody
    @PostMapping ("/save")
    public ResultBean save(@RequestBody BookCategory bookCategory) {

        if (bookCategoryService.save(bookCategory) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

    /**
     * 查看
     *
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/detail")
    public ResultBean detail(  Integer id) {
        System.out.println("方法调用");
        BookCategory bookCategory=bookCategoryService.get(id);

        if (bookCategory!=null) {
            return ResultBean.ok(bookCategory);

        }
        return ResultBean.error();
    }

    /**
     * 更新
     *
     * @param authorCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResultBean update(@RequestBody BookCategory bookCategory) {

        if (bookCategoryService.update(bookCategory) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @PostMapping("/remove")
    public ResultBean remove(Integer id) {
        if (bookCategoryService.remove(id) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/batchRemove")
    public ResultBean remove(@RequestParam("ids[]") Integer[] ids) {
        if (bookCategoryService.batchRemove(ids) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

}
