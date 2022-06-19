package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.core.utils.MD5Util;
import com.novel.core.utils.ResponseStatus;
import com.novel.core.utils.ResultBean;
import com.novel.core.utils.StatUtil;
import com.novel.entity.OrderPay;
import com.novel.entity.SysUser;
import com.novel.service.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.Charsets;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("sysUser")
@RequiredArgsConstructor
public class SysUserController {
    private final SysUserService sysUserService;
    private final UserService userService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final PayService payService;

    @GetMapping("/login")
    @ResponseBody
    public ResultBean login(@RequestParam String username, @RequestParam String password) {

        SysUser sysUser = sysUserService.login(username);

        String pass = sysUser.getPassword();
        System.out.println(MD5Util.MD5Encode(password, Charsets.UTF_8.name()));
        if (pass.equals(MD5Util.MD5Encode(password, Charsets.UTF_8.name()))) {
            return ResultBean.ok(sysUser);

        }
        return ResultBean.fail(ResponseStatus.NO_LOGIN);
    }

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
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserService.list();
        if (list == null && list.size() == 0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<SysUser> pageInfo = new PageInfo<SysUser>(list);
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
    @GetMapping("/save")
    public ResultBean save(SysUser sysUser) {
        if (sysUserService.save(sysUser) > 0) {
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
        if (sysUserService.get(id) != null) {
            return ResultBean.ok(sysUserService.get(id));
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
    public ResultBean update(@RequestBody SysUser sysUser) {
        System.out.println(sysUser.getPassword());
        String uppass = MD5Util.MD5Encode(sysUser.getPassword(), Charsets.UTF_8.name());
        sysUser.setPassword(uppass);
        if (sysUserService.update(sysUser) > 0) {
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
        if (sysUserService.remove(id) > 0) {
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
    public ResultBean remove(@RequestParam("ids[]") Long[] ids) {
        if (sysUserService.batchRemove(ids) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }
    @ResponseBody
    @GetMapping("/countSta")
    public ResultBean countStat(){
        Map map=new HashMap<>(0);
        StatUtil statUtil=new StatUtil();
        statUtil.setUserNumber(userService.count(map));
        statUtil.setAuthorNumber(authorService.count(map));
        statUtil.setBookNumber(bookService.count(map));
        statUtil.setOrderNumber(payService.count());
        System.out.println(statUtil.toString());
        if (statUtil!=null){
            return ResultBean.ok(statUtil);
        }
        return ResultBean.error();
    }
}
