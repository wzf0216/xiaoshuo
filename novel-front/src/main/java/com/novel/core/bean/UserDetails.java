package com.novel.core.bean;

import lombok.Data;


@Data
public class UserDetails {
    //用户id
    private Long id;
    //用户名
    private String username;
    //用户状态
    private Byte status;
}
