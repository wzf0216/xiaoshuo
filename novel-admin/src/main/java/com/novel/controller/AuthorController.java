package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.core.utils.ResponseStatus;
import com.novel.core.utils.ResultBean;
import com.novel.entity.Author;
import com.novel.entity.AuthorCode;
import com.novel.entity.User;
import com.novel.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

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
        List<Author> list = authorService.list();
        if (list == null && list.size() == 0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<Author> pageInfo = new PageInfo<Author>(list);
        if (pageInfo.getSize() > 0) {
            return ResultBean.ok(pageInfo);
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
    public ResultBean detail( Long id) {
        Author author=authorService.get(id);
        if (author!=null) {
            return ResultBean.ok(author);
        }
        return ResultBean.error();
    }
    @ResponseBody
    @GetMapping("/search")
    public ResultBean search(@RequestParam(value="penname")String penname,@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Author> list=authorService.search(penname);
        if (list == null&&list.size()==0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<Author>pageInfo =new PageInfo<Author>(list);
        if (pageInfo.getSize() > 0) {
            return ResultBean.ok(pageInfo);
        }
        return ResultBean.error();
    }
    /**
     * 添加
     *
     * @param author
     * @return
     */
    @GetMapping("/save")
    public ResultBean save(Author author) {
        if (authorService.save(author) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

    /**
     * 更新
     *
     * @param author
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResultBean update(@RequestBody Author author) {

        System.out.println(author.getId());
        if (authorService.update(author) > 0) {
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
    public ResultBean remove(Long id) {
        if (authorService.remove(id) > 0) {
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
    public ResultBean remove(@RequestParam("ids[]") Long[] ids) {
        if (authorService.batchRemove(ids) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }
}
