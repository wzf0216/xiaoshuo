package com.novel.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.novel.entity.UserReadHistory;
import lombok.Data;

import java.util.Date;


@Data
public class BookReadHistoryVO extends UserReadHistory {

    private Integer catId;
    private String catName;
    private Long lastIndexId;

    private String lastIndexName;
    private String bookName;

    @JsonFormat(timezone = "GMT+8", pattern = "MM/dd HH:mm:ss")
    private Date lastIndexUpdateTime;


    @Override
    public String toString() {
        return super.toString();
    }
}
