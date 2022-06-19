package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.core.utils.ResponseStatus;
import com.novel.core.utils.ResultBean;
import com.novel.entity.Author;
import com.novel.entity.Book;
import com.novel.entity.BookCategory;
import com.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {
    private final BookService bookService;
    /**分页浏览
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public ResultBean list(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Book> list=bookService.list();
        if (list == null&&list.size()==0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<Book>pageInfo =new PageInfo<Book>(list);
        if (pageInfo.getSize() > 0) {
            return ResultBean.ok(pageInfo);
        }
        return ResultBean.error();
    }
    @ResponseBody
    @GetMapping("/search")
    public ResultBean search(@RequestParam(value="bookname")String bookname,@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        System.out.println("调用方法");
        PageHelper.startPage(pageNum,pageSize);
        List<Book> list=bookService.search(bookname);
        if (list == null&&list.size()==0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<Book>pageInfo =new PageInfo<Book>(list);
        if (pageInfo.getSize() > 0) {
            return ResultBean.ok(pageInfo);
        }
        return ResultBean.error();
    }
    /**添加
     * @param authorCode
     * @return
     */
    @ResponseBody
    @GetMapping("/save")
    public ResultBean save(Book book) {
        if (bookService.save(book) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

    /**查看
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/detail")
    public ResultBean detail( Long id) {
        System.out.println("小说调用"+id);
        Book book=bookService.get(id);
        if (book!= null) {
            return ResultBean.ok(bookService.get(id));
        }
        return ResultBean.error();
    }

    /**更新
     * @param authorCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResultBean update(@RequestBody Book book) {

        if (bookService.update(book) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

    /**删除
     * @param id
     * @return
     */
    @PostMapping("/remove")
    public ResultBean remove(Long id) {
        if (bookService.remove(id) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

    /**批量删除
     * @param ids
     * @return
     */
    @ResponseBody
    @PostMapping("/batchRemove")
    public ResultBean remove(@RequestParam("ids[]") Long[] ids) {
        if (bookService.batchRemove(ids) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

}
