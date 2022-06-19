package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.core.utils.ResultBean;
import com.novel.entity.SysUser;
import com.novel.entity.User;
import com.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.novel.core.utils.ResponseStatus;
import java.sql.PreparedStatement;
import java.util.List;

@Controller
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    /**分页浏览
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public ResultBean list(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list=userService.list();
        if (list == null&&list.size()==0) {
            System.out.println("信息为空");
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<User>pageInfo =new PageInfo<User>(list);
        if (pageInfo.getSize() > 0) {
            return ResultBean.ok(pageInfo);
        }
        return ResultBean.error();
    }
    @ResponseBody
    @GetMapping("/search")
    public ResultBean search(@RequestParam(value="username")String username,@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list=userService.search(username);
        if (list == null&&list.size()==0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<User>pageInfo =new PageInfo<User>(list);
        if (pageInfo.getSize() > 0) {
            return ResultBean.ok(pageInfo);
        }
        return ResultBean.error();
    }

    /**添加
     * @param authorCode
     * @return
     */
    @GetMapping("/save")
    public ResultBean save(User user) {
        if (userService.save(user) > 0) {
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
        System.out.println("用户调用");
        User user=userService.get(id);
        if (user != null) {
            return ResultBean.ok(user);
        }
        return ResultBean.error();
    }

    /**更新
     * @param authorCode
     * @return
     */
    @ResponseBody
    @RequestMapping("/update")
    public ResultBean update(@RequestBody User user) {
        System.out.println(user.getUserSex());
        if (userService.update(user) > 0) {
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
        if (userService.remove(id) > 0) {
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
        if (userService.batchRemove(ids) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }
}
