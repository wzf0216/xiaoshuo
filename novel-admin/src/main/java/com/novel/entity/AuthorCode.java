package com.novel.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.novel.core.utils.LongToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.annotation.Generated;
@Data
public class AuthorCode {

    //主键
    //java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
    //所以通过序列化成字符串来解决
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    //邀请码
    private String inviteCode;
    //有效时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date validityTime;
    //是否使用过，0：未使用，1:使用过
    private Integer isUse;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //创建人ID
    //java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
    //所以通过序列化成字符串来解决
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long createUserId;
}