package com.novel.controller;

import com.novel.core.bean.ResultBean;
import com.novel.service.FriendLinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("friendLink")
@RestController
@Slf4j
@RequiredArgsConstructor

public class FriendLinkController {

    private final FriendLinkService friendLinkService;

    /**
     * 查询首页友情链接
     * */
    @PostMapping("listIndexLink")
    public ResultBean listIndexLink(){
        return ResultBean.ok(friendLinkService.listIndexLink());
    }




}
