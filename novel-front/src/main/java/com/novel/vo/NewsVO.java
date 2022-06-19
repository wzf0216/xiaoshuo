package com.novel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.novel.entity.News;
import lombok.Data;

import java.util.Date;


@Data
public class NewsVO extends News {

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date createTime;
}
