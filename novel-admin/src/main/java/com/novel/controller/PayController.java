package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.novel.core.utils.ResultBean;
import com.novel.entity.News;
import com.novel.entity.OrderPay;
import com.novel.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.novel.core.utils.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping("pay")
@RequiredArgsConstructor

public class PayController {
    private final PayService payService;


    /**分页浏览
     * @param page
     * @param pageSize
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public ResultBean list(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum, @RequestParam(defaultValue = "5", value = "pageSize") Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<OrderPay> list=payService.list();
        if (list == null&&list.size()==0) {
            return ResultBean.fail(ResponseStatus.DATA_NULL);
        }
        PageInfo<OrderPay>pageInfo =new PageInfo<OrderPay>(list);
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
    public ResultBean save(OrderPay orderPay) {
        if (payService.save(orderPay) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

    /**查看
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public ResultBean detail(@PathVariable("id") Long id) {
        if (payService.get(id) != null) {
            return ResultBean.ok(payService.get(id));
        }
        return ResultBean.error();
    }

    /**更新
     * @param authorCode
     * @return
     */
    @RequestMapping("/update")
    public ResultBean update(OrderPay orderPay) {

        if (payService.update(orderPay) > 0) {
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
        if (payService.remove(id) > 0) {
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
        if (payService.batchRemove(ids) > 0) {
            return ResultBean.ok();
        }
        return ResultBean.error();
    }

}
