package com.novel.vo;

import com.novel.entity.BookSetting;
import lombok.Data;

import java.io.Serializable;


@Data
public class BookSettingVO extends BookSetting implements Serializable {

    private String bookName;

    private String picUrl;

    private String authorName;

    private String bookDesc;

    private Float score;


    @Override
    public String toString() {
        return super.toString();
    }
}
