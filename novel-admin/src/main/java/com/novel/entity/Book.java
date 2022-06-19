package com.novel.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.novel.core.utils.LongToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.annotation.Generated;
@Data
public class Book {

    @JsonSerialize(using = LongToStringSerializer.class)
    private Long id;
    //作品方向，0：男频，1：女频'
    private Integer workDirection;
    //分类ID
    private Integer catId;
    //分类名
    private String catName;

    //小说封面
    private String picUrl;
    //小说名
    private String bookName;

    //作者id
    //java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
    //所以通过序列化成字符串来解决
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long authorId;
    //作者名
    private String authorName;
    //书籍描述
    private String bookDesc;
    //评分，预留字段
    private Float score;
    //书籍状态，0：连载中，1：已完结
    private Integer bookStatus;
    //点击量
    //java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
    //所以通过序列化成字符串来解决
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long visitCount;
    //总字数
    private Integer wordCount;
    //评论数
    private Integer commentCount;

    //最新目录ID
    //java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
    //所以通过序列化成字符串来解决
    @JsonSerialize(using = LongToStringSerializer.class)
    private Long lastIndexId;
    //最新目录名
    private String lastIndexName;
    //最新目录更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastIndexUpdateTime;
    //是否收费，1：收费，0：免费
    private Integer isVip;
    //状态，0：入库，1：上架
    private Integer status;
    //更新时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    //创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    //爬虫源站ID
    private Integer crawlSourceId;
    //抓取的源站小说ID
    private String crawlBookId;
    //最后一次的抓取时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crawlLastTime;
    //是否已停止更新，0：未停止，1：已停止
    private Integer crawlIsStop;


}