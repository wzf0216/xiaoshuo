package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.core.utils.ResponseStatus;
import com.novel.core.utils.ResultBean;
import com.novel.entity.Author;
import com.novel.entity.AuthorCode;
import com.novel.service.AuthorCodeService;
import com.sun.org.apache.regexp.internal.RE;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("authorCode")
@RequiredArgsConstructor
public class AuthorCodeController {
    private final AuthorCodeService codeService;

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
        List<AuthorCode> list = codeService.list();
        if (list == null && list.size() == 0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<AuthorCode> pageInfo = new PageInfo<AuthorCode>(list);
        if (pageInfo.getSize() > 0) {
            return ResultBean.ok(pageInfo);
        }
        return ResultBean.error();
    }
    @ResponseBody
    @GetMapping("/search")
    public ResultBean search(@RequestParam(value="code")String code,@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<AuthorCode> list=codeService.search(code);
        if (list == null&&list.size()==0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<AuthorCode>pageInfo =new PageInfo<AuthorCode>(list);
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
    @PostMapping("/save")
    public ResultBean save(@RequestBody AuthorCode authorCode) {
        authorCode.setIsUse(0);
        if (codeService.save(authorCode) > 0) {
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
    public ResultBean detail(Long id) {
        if (codeService.get(id) != null) {
            return ResultBean.ok(codeService.get(id));
        }
        return ResultBean.error();
    }

    /**
     * 更新
     *
     * @param authorCode
     * @return
     */
    @RequestMapping("/update")
    public ResultBean update(AuthorCode authorCode) {

        if (codeService.update(authorCode) > 0) {
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
        if (codeService.remove(id) > 0) {
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

    @PostMapping("/batchRemove")
    @ResponseBody
    public ResultBean remove(@RequestParam("ids[]")Long[] ids) {

        System.out.println(ids.length);
        if (codeService.batchRemove(ids) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

}
