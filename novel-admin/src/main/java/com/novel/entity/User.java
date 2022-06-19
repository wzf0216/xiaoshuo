package com.novel.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.novel.core.utils.LongToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
@Data
public class User implements Serializable {
    //主键
    //java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
    //所以通过序列化成字符串来解决
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    //登录名
    private String username;
    //登录密码
    private String password;
    //昵称
    private String nickName;
    //用户头像
    private String userPhoto;
    //用户性别，0：男，1：女
    private Integer userSex;
    //账户余额
    //java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
    //所以通过序列化成字符串来解决
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long accountBalance;
    //用户状态，0：正常
    private Integer status;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
