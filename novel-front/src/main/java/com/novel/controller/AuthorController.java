package com.novel.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.novel.core.bean.ResultBean;
import com.novel.core.bean.UserDetails;
import com.novel.core.enums.ResponseStatus;
import com.novel.core.exception.BusinessException;
import com.novel.entity.Author;
import com.novel.entity.Book;
import com.novel.service.AuthorService;
import com.novel.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RequestMapping("author")
@RestController
@Slf4j
@RequiredArgsConstructor

public class AuthorController extends BaseController{

    private  final AuthorService authorService;

    private  final BookService bookService;

    /**
     * 校验笔名是否存在
     * */
    @PostMapping("checkPenName")
    public ResultBean checkPenName(String penName){

        return ResultBean.ok(authorService.checkPenName(penName));
    }

    /**
     * 作家发布小说分页列表查询
     * */
    @PostMapping("listBookByPage")
    public ResultBean listBookByPage(@RequestParam(value = "curr", defaultValue = "1") int curr, @RequestParam(value = "limit", defaultValue = "5") int limit ,HttpServletRequest request){
        PageHelper.startPage(1,10);
        List<Book> list=bookService.listBookPageByUserId(getUserDetails(request).getId());

           PageInfo<Book> pageInfo=new PageInfo<Book>(list);
        System.out.println(pageInfo);
        return ResultBean.ok(pageInfo);
    }

    /**
     * 发布小说
     * */
    @PostMapping("addBook")
    public ResultBean addBook(@RequestParam("bookDesc") String bookDesc,Book book,HttpServletRequest request){

//检验作者
        Author author = checkAuthor(request);
        //设置章节内容排版
        book.setBookDesc(bookDesc
                .replaceAll("\\n","<br>")
                .replaceAll("\\s","&nbsp;"));
        //发布小说
        bookService.addBook(book,author.getId(),author.getPenName());

        return ResultBean.ok();
    }

    /**
     * 更新小说状态,上架或下架
     * */
    @PostMapping("updateBookStatus")
    public ResultBean updateBookStatus(Long bookId,Byte status,HttpServletRequest request){
//检验作者
        Author author = checkAuthor(request);
        //更新小说状态,上架或下架
        bookService.updateBookStatus(bookId,status,author.getId());

        return ResultBean.ok();
    }

    /**
     * 删除章节
     */
    @PostMapping("deleteIndex")
    public ResultBean deleteIndex(Long indexId,  HttpServletRequest request) {

        Author author = checkAuthor(request);

        //删除章节
        bookService.deleteIndex(indexId, author.getId());

        return ResultBean.ok();
    }

    /**
     * 更新章节名
     */
    @PostMapping("updateIndexName")
    public ResultBean updateIndexName(Long indexId,  String indexName, HttpServletRequest request) {

        Author author = checkAuthor(request);

        //更新章节名
        bookService.updateIndexName(indexId, indexName, author.getId());

        return ResultBean.ok();
    }


    /**
     * 发布章节内容
     * */
    @PostMapping("addBookContent")
    public ResultBean addBookContent(Long bookId, String indexName, String content,Byte isVip, HttpServletRequest request) {
        Author author = checkAuthor(request);

        content = content.replaceAll("\\n", "<br>")
                .replaceAll("\\s", "&nbsp;");
        //发布章节内容
        bookService.addBookContent(bookId, indexName, content,isVip, author.getId());

        return ResultBean.ok();
    }
    /**
     * 查询章节内容
     */
    @PostMapping("queryIndexContent")
    public ResultBean queryIndexContent(Long indexId,  HttpServletRequest request) {

        Author author = checkAuthor(request);
//得到章节内容
        String content = bookService.queryIndexContent(indexId, author.getId());
//设置排版
        content = content.replaceAll("<br>", "\n")
                .replaceAll("&nbsp;", " ");
        return ResultBean.ok(content);
    }

    /**
     * 更新章节内容
     */
    @PostMapping("updateBookContent")
    public ResultBean updateBookContent(Long indexId, String indexName, String content, HttpServletRequest request) {
        Author author = checkAuthor(request);
//设置排版
        content = content.replaceAll("\\n", "<br>")
                .replaceAll("\\s", "&nbsp;");

        //更新章节内容
        bookService.updateBookContent(indexId, indexName, content, author.getId());

        return ResultBean.ok();
    }
    //检验作者状态
    private Author checkAuthor(HttpServletRequest request) {
//得到作者id  用户名称
        UserDetails userDetails = getUserDetails(request);
        if (userDetails == null) {
            throw new BusinessException(ResponseStatus.NO_LOGIN);
        }

        //查询作家信息
        Author author = authorService.queryAuthor(userDetails.getId());

        //判断作者状态是否正常
        if (author.getStatus() == 1) {
            //封禁状态，不能发布小说
            throw new BusinessException(ResponseStatus.AUTHOR_STATUS_FORBIDDEN);
        }


        return author;


    }


}
