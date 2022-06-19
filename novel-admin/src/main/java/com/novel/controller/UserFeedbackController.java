package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.core.utils.ResponseStatus;
import com.novel.core.utils.ResultBean;
import com.novel.entity.OrderPay;
import com.novel.entity.UserFeedback;
import com.novel.service.UserFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

@RequestMapping("UserFeedback")
public class UserFeedbackController {
    private final UserFeedbackService userFeedbackService;

    @ResponseBody
    @GetMapping("/list")
    public ResultBean list(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        System.out.println("调用方法");
        PageHelper.startPage(pageNum,pageSize);
        List<UserFeedback> list=userFeedbackService.list();
        System.out.println(list.size());
        if (list == null&&list.size()==0) {
            System.out.println("信息为空");
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<UserFeedback> pageInfo =new PageInfo<UserFeedback>(list);

        if (pageInfo.getSize() > 0) {
            return ResultBean.ok(pageInfo);
        }
        return ResultBean.error();
    }

    /**添加
     * @param
     * @return
     */
    @GetMapping("/save")
    public ResultBean save(UserFeedback userFeedback) {
        if (userFeedbackService.save(userFeedback) > 0) {
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
    public ResultBean detail( Long id){
        if (userFeedbackService.get(id) != null) {
            return ResultBean.ok(userFeedbackService.get(id));
        }
        return ResultBean.error();
    }

    /**更新
     * @param
     * @return
     */
    @RequestMapping("/update")
    public ResultBean update(UserFeedback userFeedback) {

        if (userFeedbackService.update(userFeedback) > 0) {
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
        if (userFeedbackService.remove(id) > 0) {
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
        if (userFeedbackService.batchRemove(ids) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }
}
