package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.core.utils.ResultBean;
import com.novel.entity.News;
import com.novel.core.utils.ResponseStatus;

import com.novel.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    /**分页浏览
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public ResultBean list(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<News> list=newsService.list();
        if (list == null&&list.size()==0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<News>pageInfo =new PageInfo<News>(list);
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
    @PostMapping ("/save")
    public ResultBean save(@RequestBody News news) {
        System.out.println("diaoyong");
        if (newsService.save(news) > 0) {
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
        System.out.println("调用new");
        if (newsService.get(id) != null) {
            return ResultBean.ok(newsService.get(id));
        }
        return ResultBean.error();
    }

    /**更新
     * @param authorCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResultBean update(@RequestBody News news) {

        if (newsService.update(news) > 0) {
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
        if (newsService.remove(id) > 0) {
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
        if (newsService.batchRemove(ids) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

}
