package com.novel.search;

import lombok.Data;

import java.util.Date;

/**
 * 小说搜索参数
 *
 */
@Data
public class BookSP {

    private String keyword;//搜索框键入的值

    private Byte workDirection;//作品方向

    private Integer catId;//分类

    private Byte isVip;//是否付费

    private Byte bookStatus;//

    private Integer wordCountMin;//字数最少

    private Integer wordCountMax;//字数最大

    private Date updateTimeMin;//最新更新

    private Long updatePeriod;//更新时期

    private String sort;//排序




}
