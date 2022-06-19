package com.novel.controller;


import com.novel.core.bean.ResultBean;
import com.novel.core.cache.CacheService;
import com.novel.core.utils.Constants;
import com.novel.core.utils.RandomValidateCodeUtil;
import com.novel.core.utils.UUIDUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;


@Controller
@RequestMapping("file")
@Slf4j
@RequiredArgsConstructor

public class FileController {

    private  final CacheService cacheService;
    @Value("${pic.save.path}")
    private String picSavePath;

    /**
     * 生成验证码
     */
    @GetMapping(value = "getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            //输出验证码图片方法
            randomValidateCode.getRandcode(cacheService, response);
        } catch (Exception e) {
            log.error("获取验证码失败....", e);
        }
    }

    /**
     * 文件上传
     */
    @ResponseBody
    @PostMapping("/upload")
    ResultBean upload(@RequestParam("file") MultipartFile file) {
        //获取系统当前时间
        Date currentDate = new Date();
        try {
            //生成保存路径 设置的路径+年/月/日
            String savePath =
                    Constants.LOCAL_PIC_PREFIX + DateUtils.formatDate(currentDate, "yyyy") + "/" +
                            DateUtils.formatDate(currentDate, "MM") + "/" +
                            DateUtils.formatDate(currentDate, "dd") ;
            //得到图片的原始文件名
            String oriName = file.getOriginalFilename();
            //设置本地保存文件名32位随机+文件类型
            String saveFileName = UUIDUtil.getUUID32() + oriName.substring(oriName.lastIndexOf("."));
            //创建保存文件
            File saveFile = new File( picSavePath + savePath, saveFileName);
            //保存文件是否创建
            if (!saveFile.getParentFile().exists()) {
                saveFile.getParentFile().mkdirs();
            }
//            file.transferTo(saveFile);
            //复制图片到本地文件夹
            FileUtils.copyInputStreamToFile(file.getInputStream(),saveFile);
            return ResultBean.ok(savePath+"/"+saveFileName);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResultBean.error();
        }

    }
}
