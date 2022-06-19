package com.novel.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.novel.core.utils.LongToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class Author implements Serializable {
    //主键
    //java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
    //所以通过序列化成字符串来解决
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    //用户ID
    //java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
    //所以通过序列化成字符串来解决
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long userId;
    //邀请码
    private String inviteCode;
    //笔名
    private String penName;
    //手机号码
    private String telPhone;
    //QQ或微信账号
    private String chatAccount;
    //电子邮箱
    private String email;
    //作品方向，0：男频，1：女频
    private Integer workDirection;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //0：正常，1：封禁
    private Integer status;
}
