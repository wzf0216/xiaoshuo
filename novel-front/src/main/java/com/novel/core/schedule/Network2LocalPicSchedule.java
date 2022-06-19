package com.novel.core.schedule;


import com.novel.core.utils.Constants;
import com.novel.entity.Book;
import com.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 网络图片转存本地任务
 * @author Administrator
 */
@ConditionalOnProperty(prefix = "pic.save",name = "type",havingValue = "2")
@Service
@Slf4j
@RequiredArgsConstructor
public class Network2LocalPicSchedule {

    private  final BookService bookService;

    //注入外部配置文件的值
   @Value("${pic.save.type}")
   //保存类型
   private  Integer picSaveType;
    @Value("${pic.save.path}")
    //保存路径
   private String picPath;
    /**
     * 10分钟转一次
     */
    //实现定时任务
    @Scheduled(fixedRate = 1000 * 60 * 10)
    @SneakyThrows//异常处理
    public void trans() {

        log.info("Network2LocalPicSchedule。。。。。。。。。。。。");


        List<Book> networkPicBooks = bookService.queryNetworkPicBooks(Constants.LOCAL_PIC_PREFIX,100);
        for (Book book : networkPicBooks) {
            bookService.updateBookPicToLocal(book.getPicUrl(), book.getId());
            //3秒钟转化一张图片，10分钟转化200张
            Thread.sleep(3000);
        }


    }
}
