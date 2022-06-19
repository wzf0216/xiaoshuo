package com.novel.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;

import com.novel.core.cache.CacheKey;
import com.novel.core.cache.CacheService;
import com.novel.core.enums.ResponseStatus;
import com.novel.core.exception.BusinessException;
import com.novel.core.utils.BeanUtil;
import com.novel.core.utils.Constants;
import com.novel.core.utils.FileUtil;
import com.novel.core.utils.IdWorker;
import com.novel.entity.*;
import com.novel.entity.Book;
import com.novel.mapper.FrontBookCommentMapper;
import com.novel.mapper.FrontBookMapper;
import com.novel.mapper.FrontBookSettingMapper;
import com.novel.search.BookSP;
import com.novel.service.AuthorService;
import com.novel.service.BookService;
import com.novel.vo.BookCommentVO;
import com.novel.vo.BookSettingVO;
import com.novel.vo.BookVO;
import com.novel.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.dynamic.sql.SortSpecification;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.render.RenderingStrategy;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.orderbyhelper.OrderByHelper;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.novel.mapper.BookDynamicSqlSupport.book;
import static com.novel.mapper.BookDynamicSqlSupport.*;

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static org.mybatis.dynamic.sql.select.SelectDSL.select;
import static com.novel.mapper.BookCategoryDynamicSqlSupport.bookCategory;
import static com.novel.mapper.BookCommentDynamicSqlSupport.bookComment;
import static com.novel.mapper.BookContentDynamicSqlSupport.bookContent;
import static com.novel.mapper.BookContentDynamicSqlSupport.content;
import static com.novel.mapper.BookIndexDynamicSqlSupport.bookIndex;


@Service
//构造注入
@RequiredArgsConstructor

public class BookServiceImpl implements BookService {

    /**
     * 本地图片保存路径
     * */
    @Value("${pic.save.path}")
    private String picSavePath;

    private final FrontBookSettingMapper bookSettingMapper;

    private final FrontBookMapper bookMapper;

    private final BookCategoryMapper bookCategoryMapper;

    private final BookIndexMapper bookIndexMapper;

    private final BookContentMapper bookContentMapper;

    private final FrontBookCommentMapper bookCommentMapper;

    private final BookAuthorMapper bookAuthorMapper;

   private final CacheService cacheService;
    private final AuthorService authorService;


//异常抛出
    @SneakyThrows
    @Override
    //首页小说设置
    public Map<Byte, List<BookSettingVO>> listBookSettingVO() {
        //加载首页小说设置缓存
        String result = cacheService.get(CacheKey.INDEX_BOOK_SETTINGS_KEY);
        cacheService.del(CacheKey.INDEX_BOOK_SETTINGS_KEY);
        if (result == null || result.length() < Constants.OBJECT_JSON_CACHE_EXIST_LENGTH) {
            //获得首页小说集合
            List<BookSettingVO> list = bookSettingMapper.listVO();
            if(list.size() == 0) {
                //如果首页小说没有被设置，则初始化首页小说设置
                list = initIndexBookSetting();
            }
            /**
             * 使用writeValueAsString方法将小说集合转换为json格式并返回一个String对象
             *list.stream().collect对小说集合执行分组操作
             * Collectors.groupingBy根据小说设置类型进行分组
             * 类型，0：轮播图，1：顶部小说栏设置，2：本周强推，3：热门推荐，4：精品推荐
             */
            result = new ObjectMapper().writeValueAsString(list.stream().collect(Collectors.groupingBy(BookSettingVO::getType)));
           // System.out.println("小说设置"+result);
            //重新设置小说缓存
            cacheService.set(CacheKey.INDEX_BOOK_SETTINGS_KEY, result);
        }
        //ObjectMapper().readValue将result转换为Map集合
        return new ObjectMapper().readValue(result,Map.class);
    }


    /**
     * 获取首页小说设置
     * */
    private List<BookSettingVO> initIndexBookSetting() {
        Date currentDate = new Date();
        //得到评分排行前31的小说
        List<Book> books = bookMapper.selectIdsByScoreAndRandom(Constants.INDEX_BOOK_SETTING_NUM);
        if (books.size() == Constants.INDEX_BOOK_SETTING_NUM) {
            List<BookSetting> bookSettingList = new ArrayList<>(Constants.INDEX_BOOK_SETTING_NUM);
            List<BookSettingVO> bookSettingVOList = new ArrayList<>(Constants.INDEX_BOOK_SETTING_NUM);
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                byte type;
                //设置轮播图 5
                if (i < 4) {
                    type = 0;
                } else if (i < 14) {//设置顶部小说 10
                    type = 1;
                } else if (i < 19) {//设置本周强推 5
                    type = 2;
                } else if (i < 25) {//设置热门推荐 6
                    type = 3;
                } else {//设置精品推荐 6
                    type = 4;
                }
                BookSettingVO bookSettingVO = new BookSettingVO();
                BookSetting bookSetting = new BookSetting();
                //设置首页小说设置属性
                bookSetting.setType(type);
                bookSetting.setSort((byte) i);
                bookSetting.setBookId(book.getId());
                bookSetting.setCreateTime(currentDate);
                bookSetting.setUpdateTime(currentDate);
                bookSettingList.add(bookSetting);
//将book、bookSetting中与bookSettingVO相同的属性复制到bookSettingVO中
                BeanUtils.copyProperties(book, bookSettingVO);
                BeanUtils.copyProperties(bookSetting, bookSettingVO);
                bookSettingVOList.add(bookSettingVO);
            }
//将首页小说存入数据库
            bookSettingMapper.insertMultiple(bookSettingList);

            return bookSettingVOList;
        }
        return new ArrayList<>(0);
    }

    /**
     * 查询首页点击榜单数据
     * @return
     * */
    @Override
    public List<Book> listClickRank() {
        //获得点击榜缓存
        List<Book> result = (List<Book>) cacheService.getObject(CacheKey.INDEX_CLICK_BANK_BOOK_KEY);
        if (result == null || result.size() == 0) {
            //设置点击榜小说数
            result = listRank((byte) 0, 10);
            //设置缓存
            cacheService.setObject(CacheKey.INDEX_CLICK_BANK_BOOK_KEY, result, 5000);
        }
        return result;
    }
    /**
     * 查询首页新书榜单数据
     * @return
     * */
    @Override
    public List<Book> listNewRank() {
        List<Book> result = (List<Book>) cacheService.getObject(CacheKey.INDEX_NEW_BOOK_KEY);
        if (result == null || result.size() == 0) {
            //设置新书榜大小
            result = listRank((byte) 1, 10);
            cacheService.setObject(CacheKey.INDEX_NEW_BOOK_KEY, result, 3600);
        }
        return result;
    }
    /**
     * 查询首页更新榜单数据
     * @return
     * */
    @Override
    public List<BookVO> listUpdateRank() {
        List<BookVO> result = (List<BookVO>) cacheService.getObject(CacheKey.INDEX_UPDATE_BOOK_KEY);
        if (result == null || result.size() == 0) {
            List<Book> bookPOList = listRank((byte) 2, 10);
            //将book集合转换为BookVo集合
            result = BeanUtil.copyList(bookPOList,BookVO.class);
            cacheService.setObject(CacheKey.INDEX_UPDATE_BOOK_KEY, result, 60 * 10);
        }
        return result;
    }
    /**
     * 分页搜索
     * @param params 搜索参数
     * @param page 页码
     * @param pageSize 分页大小
     * @return 小说集合
     * */
    @Override
    public List<BookVO> searchByPage(BookSP params, int page, int pageSize) {
        //设置当前页数 和分页大小
        PageHelper.startPage(page, pageSize);
        if (params.getUpdatePeriod() != null) {
            //获得系统时间
            long cur = System.currentTimeMillis();
            //将天数转为毫秒
            long period = params.getUpdatePeriod() * 24 * 3600 * 1000;
            //计数更新天数
            long time = cur - period;
            params.setUpdateTimeMin(new Date(time));
        }
        //根据时间进行排序
        if (StringUtils.isNotBlank(params.getSort())) {
            OrderByHelper.orderBy(params.getSort() + " desc");
        }
        return bookMapper.searchByPage(params);
    }
    /**
     * 查询小说分类列表
     * @return 分类集合
     * */
    @Override
    public List<BookCategory> listBookCategory() {
        SelectStatementProvider selectStatementProvider = select(BookCategoryDynamicSqlSupport.id, BookCategoryDynamicSqlSupport.name, BookCategoryDynamicSqlSupport.workDirection)
                .from(bookCategory)
                .orderBy(BookCategoryDynamicSqlSupport.sort)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return bookCategoryMapper.selectMany(selectStatementProvider);
    }
    /**
     * 查询小说详情信息
     * @return 书籍信息
     * @param id 书籍ID*/
    @Override
    public Book queryBookDetail(Long bookId) {
        SelectStatementProvider selectStatement = select(id, catName, catId, picUrl, bookName, authorId, authorName, bookDesc, bookStatus, visitCount, wordCount, lastIndexId, lastIndexName, lastIndexUpdateTime,score,status)
                .from(book)
                .where(id, isEqualTo(bookId))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return bookMapper.selectMany(selectStatement).get(0);
    }

    /**
     * 查询目录列表
     * @param bookId 书籍ID
     * @param orderBy 排序
     *
     * @param page 查询页码
     *@param pageSize 分页大小
     *@return 目录集合
     * */
    @Override
    public List<BookIndex> queryIndexList(Long bookId, String orderBy, Integer page, Integer pageSize) {
        if (StringUtils.isNotBlank(orderBy)) {
            OrderByHelper.orderBy(orderBy);
        }
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }

        SelectStatementProvider selectStatement = select(BookIndexDynamicSqlSupport.id, BookIndexDynamicSqlSupport.bookId, BookIndexDynamicSqlSupport.indexNum, BookIndexDynamicSqlSupport.indexName, BookIndexDynamicSqlSupport.updateTime, BookIndexDynamicSqlSupport.isVip)
                .from(bookIndex)
                .where(BookIndexDynamicSqlSupport.bookId, isEqualTo(bookId))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return bookIndexMapper.selectMany(selectStatement);
    }


    /**
     * 查询目录
     * @param bookIndexId 目录ID
     * @return 目录信息
     * */
    @Override
    public BookIndex queryBookIndex(Long bookIndexId) {
        SelectStatementProvider selectStatement = select(BookIndexDynamicSqlSupport.id, BookIndexDynamicSqlSupport.bookId, BookIndexDynamicSqlSupport.indexNum, BookIndexDynamicSqlSupport.indexName, BookIndexDynamicSqlSupport.wordCount, BookIndexDynamicSqlSupport.updateTime,BookIndexDynamicSqlSupport.isVip)
                .from(bookIndex)
                .where(BookIndexDynamicSqlSupport.id, isEqualTo(bookIndexId))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return bookIndexMapper.selectMany(selectStatement).get(0);
    }
    /**
     * 查询上一章节目录ID
     * @param bookId 书籍ID
     * @param indexNum 目录号
     * @return 上一章节目录ID，没有则返回0
     * */
    @Override
    public Long queryPreBookIndexId(Long bookId, Integer indexNum) {
        SelectStatementProvider selectStatement = select(BookIndexDynamicSqlSupport.id)
                .from(bookIndex)
                .where(BookIndexDynamicSqlSupport.bookId, isEqualTo(bookId))
                .and(BookIndexDynamicSqlSupport.indexNum, isLessThan(indexNum))
                .orderBy(BookIndexDynamicSqlSupport.indexNum.descending())
                .limit(1)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        List<BookIndex> list = bookIndexMapper.selectMany(selectStatement);
        if (list.size() == 0) {
            return 0L;
        } else {
            return list.get(0).getId();
        }
    }
    /**
     * 查询下一章目录ID
     * @param bookId 书籍ID
     * @param indexNum 目录号
     * @return 下一章节目录ID，没有则返回0
     * */
    @Override
    public Long queryNextBookIndexId(Long bookId, Integer indexNum) {
        SelectStatementProvider selectStatement = select(BookIndexDynamicSqlSupport.id)
                .from(bookIndex)
                .where(BookIndexDynamicSqlSupport.bookId, isEqualTo(bookId))
                .and(BookIndexDynamicSqlSupport.indexNum, isGreaterThan(indexNum))
                .orderBy(BookIndexDynamicSqlSupport.indexNum)
                .limit(1)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        List<BookIndex> list = bookIndexMapper.selectMany(selectStatement);
        if (list.size() == 0) {
            return 0L;
        } else {
            return list.get(0).getId();
        }
    }
    /**
     * 查询章节内容
     * @param bookIndexId 目录ID
     * @return 书籍内容
     * */
    @Override
    public BookContent queryBookContent(Long bookIndexId) {
        SelectStatementProvider selectStatement = select(BookContentDynamicSqlSupport.id,BookContentDynamicSqlSupport.content)
                .from(bookContent)
                .where(BookContentDynamicSqlSupport.indexId, isEqualTo(bookIndexId))
                .limit(1)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return bookContentMapper.selectMany(selectStatement).get(0);
    }
    /**
     * 查询小说排行信息
     * @param type 排行类型，0点击排行，1新书排行，2更新排行
     * @param limit 查询条数
     * @return 小说集合
     * */
    @Override
    public List<Book> listRank(Byte type, Integer limit) {
        //动态查询
        SortSpecification sortSpecification = visitCount.descending();
        switch (type) {
            case 1: {
                //最新入库排序
                sortSpecification = createTime.descending();
                break;
            }
            case 2: {
                //最新更新时间排序
                sortSpecification = lastIndexUpdateTime.descending();
                break;
            }
            case 3: {
                //评论数量排序
                sortSpecification = commentCount.descending();
                break;
            }
            default: {
                break;
            }
        }
        SelectStatementProvider selectStatement = select(id, catId, catName, bookName, lastIndexId, lastIndexName, authorId, authorName, picUrl, bookDesc, wordCount, lastIndexUpdateTime)
                .from(book)
                .where(wordCount,isGreaterThan(0))
                .orderBy(sortSpecification)
                .limit(limit)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return bookMapper.selectMany(selectStatement);

    }
    /**
     * 增加点击次数
     * @param bookId 书籍ID
     * */
    @Override
    public void addVisitCount(Long bookId) {
        bookMapper.addVisitCount(bookId);

    }
    /**
     * 查询章节数
     * @param bookId 书籍ID
     * @return 章节数量
     * */
    @Override
    public long queryIndexCount(Long bookId) {
        SelectStatementProvider selectStatement = select(count(BookIndexDynamicSqlSupport.id))
                .from(bookIndex)
                .where(BookIndexDynamicSqlSupport.bookId, isEqualTo(bookId))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return bookIndexMapper.count(selectStatement);
    }
    /**
     * 根据分类id查询同类推荐书籍
     * @param catId 分类id
     * @return 书籍集合
     * */
    @Override
    public List<Book> listRecBookByCatId(Integer catId) {
        return bookMapper.listRecBookByCatId(catId);
    }
    /**
     * 查询首章目录ID
     * @param bookId 书籍ID
     * @return 首章目录ID
     * */
    @Override
    public Long queryFirstBookIndexId(Long bookId) {
        SelectStatementProvider selectStatement = select(BookIndexDynamicSqlSupport.id)
                .from(bookIndex)
                .where(BookIndexDynamicSqlSupport.bookId, isEqualTo(bookId))
                .orderBy(BookIndexDynamicSqlSupport.indexNum)
                .limit(1)
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return bookIndexMapper.selectMany(selectStatement).get(0).getId();
    }
    /**
     *分页查询书籍评论列表
     * @param userId 用户ID
     * @param bookId 书籍ID
     * @param page 页码
     * @param pageSize 分页大小
     * @return 评论集合
     * */
    @Override
    public List<BookCommentVO> listCommentByPage(Long userId, Long bookId, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        OrderByHelper.orderBy("t1.create_time desc");
        return bookCommentMapper.listCommentByPage(userId,bookId);
    }
    /**
     * 新增评价
     * @param userId 用户ID
     * @param comment 评论内容
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addBookComment(Long userId, BookComment comment) {
        //判断该用户是否已评论过该书籍
        SelectStatementProvider selectStatement = select(count(BookCommentDynamicSqlSupport.id))
                .from(bookComment)
                .where(BookCommentDynamicSqlSupport.createUserId,isEqualTo(userId))
                .and(BookCommentDynamicSqlSupport.bookId,isEqualTo(comment.getBookId()))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        if(bookCommentMapper.count(selectStatement)>0){
            throw new BusinessException(ResponseStatus.HAS_COMMENTS);
        }
        //增加评论
        comment.setCreateUserId(userId);
        comment.setCreateTime(new Date());
        bookCommentMapper.insertSelective(comment);
        //增加书籍评论数
        bookMapper.addCommentCount(comment.getBookId());

    }
    /**
     * 通过作者名获取或创建作者Id
     * @param authorName 作者名
     * @param workDirection 作品方向
     * @return 作者ID
     * */
    @Override
    public Long getOrCreateAuthorIdByName(String authorName, Byte workDirection) {
        Long authorId;
        SelectStatementProvider selectStatement = select(BookAuthorDynamicSqlSupport.id)
                .from(BookAuthorDynamicSqlSupport.bookAuthor)
                .where(BookAuthorDynamicSqlSupport.penName,isEqualTo(authorName))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        List<BookAuthor> bookAuthors = bookAuthorMapper.selectMany(selectStatement);
        if(bookAuthors.size()>0){
            //作者存在
            authorId = bookAuthors.get(0).getId();
        }else{
            //作者不存在，先创建作者
            Date currentDate = new Date();
            authorId = new IdWorker().nextId();
            BookAuthor bookAuthor = new BookAuthor();
            bookAuthor.setId(authorId);
            bookAuthor.setPenName(authorName);
            bookAuthor.setWorkDirection(workDirection);
            bookAuthor.setStatus((byte) 1);
            bookAuthor.setCreateTime(currentDate);
            bookAuthor.setUpdateTime(currentDate);
            bookAuthorMapper.insertSelective(bookAuthor);


        }

        return authorId;
    }

    /**
     * 查询小说ID
     * @param bookName 书名
     * @param author 作者名
     * @return 小说ID
     * */

    @Override
    public Long queryIdByNameAndAuthor(String bookName, String author) {
        //查询小说ID
        SelectStatementProvider selectStatement = select(id)
                .from(book)
                .where(BookDynamicSqlSupport.bookName,isEqualTo(bookName))
                .and(BookDynamicSqlSupport.authorName,isEqualTo(authorName))
                .build()
                .render(RenderingStrategies.MYBATIS3);
        List<Book> books = bookMapper.selectMany(selectStatement);
        if(books.size()>0){
            return books.get(0).getId();
        }
        return null;
    }
    /**
     * 根据小说ID查询目录号集合
     * @param bookId 小说ID
     * @return 目录号集合
     * */
    @Override
    public List<Integer> queryIndexNumByBookId(Long bookId) {
        SelectStatementProvider selectStatement = select(BookIndexDynamicSqlSupport.indexNum)
                .from(BookIndexDynamicSqlSupport.bookIndex)
                .where(BookIndexDynamicSqlSupport.bookId,isEqualTo(bookId))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return bookIndexMapper.selectMany(selectStatement).stream().map(BookIndex::getIndexNum).collect(Collectors.toList());
    }
    /**
     * 查询网络图片的小说
     * @param limit 查询条数
     * @param offset 开始行数
     * @return 返回小说集合
     * */

    @Override
    public List<Book> queryNetworkPicBooks(String localPicPrefix, Integer limit) {
        return bookMapper.queryNetworkPicBooks(localPicPrefix,limit);
    }
    /**
     * 更新小说网络图片到本地
     * @param picUrl 网络图片路径
     * @param bookId 小说ID
     */
    @Override
    public void updateBookPicToLocal(String picUrl, Long bookId) {

        picUrl = FileUtil.network2Local(picUrl,picSavePath, Constants.LOCAL_PIC_PREFIX);

        bookMapper.update(update(book)
                .set(BookDynamicSqlSupport.picUrl)
                .equalTo(picUrl)
                .set(updateTime)
                .equalTo(new Date())
                .where(id,isEqualTo(bookId))
                .build()
                .render(RenderingStrategies.MYBATIS3));

    }
    /**
     * 通过作者ID查询小说分页列表
     * @param userId 用户ID
     * @param page 页码
     * @param pageSize 分页大小
     * */
    @Override
    public List<Book> listBookPageByUserId(Long userId) {

        SelectStatementProvider selectStatement = select(id, bookName, picUrl, catName, visitCount, lastIndexUpdateTime, updateTime, wordCount, lastIndexName, status)
                .from(book)
                .where(authorId, isEqualTo(authorService.queryAuthor(userId).getId()))
                .orderBy(BookDynamicSqlSupport.createTime.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return bookMapper.selectMany(selectStatement);

    }
    /**
     * 发布小说
     * @param book 小说信息
     * @param authorId 作家ID
     * @param penName 作家笔名
     * */
    @Override
    public void addBook(Book book, Long authorId, String penName) {
        //判断小说名是否存在
        if(queryIdByNameAndAuthor(book.getBookName(),penName)!=null){
            //该作者发布过此书名的小说
            throw new BusinessException(ResponseStatus.BOOKNAME_EXISTS);
        };
        book.setAuthorName(penName);
        book.setAuthorId(authorId);
        book.setVisitCount(0L);
        book.setWordCount(0);
        book.setScore(6.5f);
        book.setLastIndexName("");
        book.setCreateTime(new Date());
        book.setUpdateTime(book.getCreateTime());
        bookMapper.insertSelective(book);

    }
    /**
     * 更新小说状态,上架或下架
     * @param bookId 小说ID
     * @param status 更新的状态
     * @param authorId 作者ID
     * */
    @Override
    public void updateBookStatus(Long bookId, Byte status, Long authorId) {
        bookMapper.update(update(book)
                .set(BookDynamicSqlSupport.status)
                .equalTo(status)
                .where(id,isEqualTo(bookId))
                .and(BookDynamicSqlSupport.authorId,isEqualTo(authorId))
                .build()
                .render(RenderingStrategies.MYBATIS3));
    }

    /**
     * 发布章节内容
     *
     * @param bookId    小说ID
     * @param indexName 章节名
     * @param content   章节内容
     * @param isVip     是否收费
     * @param authorId  作者ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addBookContent(Long bookId, String indexName, String content, Byte isVip, Long authorId) {

        Book book = queryBookDetail(bookId);
        if (!authorId.equals(book.getAuthorId())) {
            //并不是更新自己的小说
            return;
        }
        Long lastIndexId = new IdWorker().nextId();
        Date currentDate = new Date();
        //获得章节字数
        int wordCount = content.length();

        //更新小说主表信息
        bookMapper.update(update(BookDynamicSqlSupport.book)
                .set(BookDynamicSqlSupport.lastIndexId)
                .equalTo(lastIndexId)
                .set(BookDynamicSqlSupport.lastIndexName)
                .equalTo(indexName)
                .set(BookDynamicSqlSupport.lastIndexUpdateTime)
                .equalTo(currentDate)
                .set(BookDynamicSqlSupport.wordCount)
                .equalTo(book.getWordCount() + wordCount)
                .where(id, isEqualTo(bookId))
                .and(BookDynamicSqlSupport.authorId, isEqualTo(authorId))
                .build()
                .render(RenderingStrategies.MYBATIS3));
        //更新小说目录表
        int indexNum = 0;
        if (book.getLastIndexId() != null) {
            indexNum = queryBookIndex(book.getLastIndexId()).getIndexNum() + 1;
        }
        BookIndex lastBookIndex = new BookIndex();
        lastBookIndex.setId(lastIndexId);
        lastBookIndex.setWordCount(wordCount);
        lastBookIndex.setIndexName(indexName);
        lastBookIndex.setIndexNum(indexNum);
        lastBookIndex.setBookId(bookId);
        lastBookIndex.setIsVip(isVip);
        lastBookIndex.setCreateTime(currentDate);
        lastBookIndex.setUpdateTime(currentDate);
        bookIndexMapper.insertSelective(lastBookIndex);

        //更新小说内容表
        BookContent bookContent = new BookContent();
        bookContent.setIndexId(lastIndexId);
        bookContent.setContent(content);
        bookContentMapper.insertSelective(bookContent);


    }


    /**
     * 根据更新时间分页查询书籍列表
     *
     * @param startDate 开始时间，包括该时间
     * @param limit     查询数量
     * @return 书籍列表
     */
    @Override
    public List<Book> queryBookByUpdateTimeByPage(Date startDate, int limit) {
        return bookMapper.selectMany(select(book.allColumns())
                .from(book)
                .where(updateTime, isGreaterThan(startDate))
                .orderBy(updateTime)
                .limit(limit)
                .build()
                .render(RenderingStrategies.MYBATIS3));
    }

    /**
     * 查询作品列表
     *
     * @param authorId 作家ID
     * @return 作品列表
     */
    @Override
    public List<Book> queryBookList(Long authorId) {
        return bookMapper.selectMany(select(id, bookName)
                .from(book)
                .where(BookDynamicSqlSupport.authorId,isEqualTo(authorId))
                .build()
                .render(RenderingStrategies.MYBATIS3));
    }

    /**
     * 删除章节
     *
     * @param indexId
     * @param authorId 作家ID
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteIndex(Long indexId, Long authorId) {

        //查询小说章节表信息
        List<BookIndex> bookIndices = bookIndexMapper.selectMany(
                select(BookIndexDynamicSqlSupport.bookId, BookIndexDynamicSqlSupport.wordCount)
                        .from(bookIndex)
                        .where(BookIndexDynamicSqlSupport.id, isEqualTo(indexId)).build().render(RenderingStrategy.MYBATIS3));
        if (bookIndices.size() > 0) {
            BookIndex bookIndex = bookIndices.get(0);
            //获取小说ID
            Long bookId = bookIndex.getBookId();
            //查询小说表信息
            List<Book> books = bookMapper.selectMany(
                    select(wordCount, BookDynamicSqlSupport.authorId)
                            .from(book)
                            .where(id, isEqualTo(bookId))
                            .build()
                            .render(RenderingStrategy.MYBATIS3));
            if (books.size() > 0) {
                Book book = books.get(0);
                int wordCount = book.getWordCount();
                //作者ID相同，表明该小说是登录用户发布，可以删除
                if (book.getAuthorId().equals(authorId)) {
                    //删除目录表和内容表记录
                    bookIndexMapper.deleteByPrimaryKey(indexId);
                    bookContentMapper.delete(deleteFrom(bookContent).where(BookContentDynamicSqlSupport.indexId, isEqualTo(indexId)).build()
                            .render(RenderingStrategies.MYBATIS3));
                    //更新总字数
                    wordCount = wordCount - bookIndex.getWordCount();
                    //更新最新章节
                    Long lastIndexId = null;
                    String lastIndexName = null;
                    Date lastIndexUpdateTime = null;
                    List<BookIndex> lastBookIndices = bookIndexMapper.selectMany(
                            select(BookIndexDynamicSqlSupport.id, BookIndexDynamicSqlSupport.indexName, BookIndexDynamicSqlSupport.createTime)
                                    .from(BookIndexDynamicSqlSupport.bookIndex)
                                    .where(BookIndexDynamicSqlSupport.bookId, isEqualTo(bookId))
                                    .orderBy(BookIndexDynamicSqlSupport.indexNum.descending())
                                    .limit(1)
                                    .build()
                                    .render(RenderingStrategy.MYBATIS3));
                    if (lastBookIndices.size() > 0) {
                        BookIndex lastBookIndex = lastBookIndices.get(0);
                        lastIndexId = lastBookIndex.getId();
                        lastIndexName = lastBookIndex.getIndexName();
                        lastIndexUpdateTime = lastBookIndex.getCreateTime();

                    }
                    //更新小说主表信息
                    bookMapper.update(update(BookDynamicSqlSupport.book)
                            .set(BookDynamicSqlSupport.wordCount)
                            .equalTo(wordCount)
                            .set(updateTime)
                            .equalTo(new Date())
                            .set(BookDynamicSqlSupport.lastIndexId)
                            .equalTo(lastIndexId)
                            .set(BookDynamicSqlSupport.lastIndexName)
                            .equalTo(lastIndexName)
                            .set(BookDynamicSqlSupport.lastIndexUpdateTime)
                            .equalTo(lastIndexUpdateTime)
                            .where(id, isEqualTo(bookId))
                            .build()
                            .render(RenderingStrategies.MYBATIS3));


                }
            }


        }


    }


    /**
     * 更新章节名
     *
     * @param indexId
     * @param indexName
     * @param authorId
     */
    @Override
    public void updateIndexName(Long indexId, String indexName, Long authorId) {
//查询小说章节表信息
        List<BookIndex> bookIndices = bookIndexMapper.selectMany(
                select(BookIndexDynamicSqlSupport.bookId, BookIndexDynamicSqlSupport.wordCount)
                        .from(bookIndex)
                        .where(BookIndexDynamicSqlSupport.id, isEqualTo(indexId)).build().render(RenderingStrategy.MYBATIS3));
        if (bookIndices.size() > 0) {
            BookIndex bookIndex = bookIndices.get(0);
            //获取小说ID
            Long bookId = bookIndex.getBookId();
            //查询小说表信息
            List<Book> books = bookMapper.selectMany(
                    select(wordCount, BookDynamicSqlSupport.authorId)
                            .from(book)
                            .where(id, isEqualTo(bookId))
                            .build()
                            .render(RenderingStrategy.MYBATIS3));
            if (books.size() > 0) {
                Book book = books.get(0);
                //作者ID相同，表明该小说是登录用户发布，可以修改
                if (book.getAuthorId().equals(authorId)) {

                    bookIndexMapper.update(
                            update(BookIndexDynamicSqlSupport.bookIndex)
                                    .set(BookIndexDynamicSqlSupport.indexName)
                                    .equalTo(indexName)
                                    .set(BookIndexDynamicSqlSupport.updateTime)
                                    .equalTo(new Date())
                                    .where(BookIndexDynamicSqlSupport.id, isEqualTo(indexId))
                                    .build()
                                    .render(RenderingStrategy.MYBATIS3));


                }
            }


        }
    }

    /**
     * 查询章节内容
     *
     * @param indexId
     * @param authorId
     * @return
     */
    @Override
    public String queryIndexContent(Long indexId, Long authorId) {
        //查询小说章节表信息
        List<BookIndex> bookIndices = bookIndexMapper.selectMany(
                select(BookIndexDynamicSqlSupport.bookId, BookIndexDynamicSqlSupport.wordCount)
                        .from(bookIndex)
                        .where(BookIndexDynamicSqlSupport.id, isEqualTo(indexId)).build().render(RenderingStrategy.MYBATIS3));
        if (bookIndices.size() > 0) {
            BookIndex bookIndex = bookIndices.get(0);
            //获取小说ID
            Long bookId = bookIndex.getBookId();
            //查询小说表信息
            List<Book> books = bookMapper.selectMany(
                    select(wordCount, BookDynamicSqlSupport.authorId)
                            .from(book)
                            .where(id, isEqualTo(bookId))
                            .build()
                            .render(RenderingStrategy.MYBATIS3));
            if (books.size() > 0) {
                Book book = books.get(0);
                //作者ID相同，表明该小说是登录用户发布
                if (book.getAuthorId().equals(authorId)) {
                    return bookContentMapper.selectMany(
                            select(content)
                                    .from(bookContent)
                                    .where(BookContentDynamicSqlSupport.indexId, isEqualTo(indexId))
                                    .limit(1)
                                    .build().render(RenderingStrategy.MYBATIS3))
                            .get(0).getContent();
                }

            }
        }
        return "";
    }

    /**
     * 更新章节内容
     *
     * @param indexId
     * @param indexName
     * @param content
     * @param authorId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBookContent(Long indexId, String indexName, String content, Long authorId) {

        //查询小说章节表信息
        List<BookIndex> bookIndices = bookIndexMapper.selectMany(
                select(BookIndexDynamicSqlSupport.bookId, BookIndexDynamicSqlSupport.wordCount)
                        .from(bookIndex)
                        .where(BookIndexDynamicSqlSupport.id, isEqualTo(indexId)).build().render(RenderingStrategy.MYBATIS3));
        if (bookIndices.size() > 0) {
            BookIndex bookIndex = bookIndices.get(0);
            //获取小说ID
            Long bookId = bookIndex.getBookId();
            //查询小说表信息
            List<Book> books = bookMapper.selectMany(
                    select(wordCount, BookDynamicSqlSupport.authorId)
                            .from(book)
                            .where(id, isEqualTo(bookId))
                            .build()
                            .render(RenderingStrategy.MYBATIS3));
            if (books.size() > 0) {
                Book book = books.get(0);
                //作者ID相同，表明该小说是登录用户发布，可以修改
                if (book.getAuthorId().equals(authorId)) {
                    Date currentDate = new Date();
                    int wordCount = content.length();

                    //更新小说目录表
                    int update = bookIndexMapper.update(
                            update(BookIndexDynamicSqlSupport.bookIndex)
                                    .set(BookIndexDynamicSqlSupport.indexName)
                                    .equalTo(indexName)
                                    .set(BookIndexDynamicSqlSupport.wordCount)
                                    .equalTo(wordCount)
                                    .set(BookIndexDynamicSqlSupport.updateTime)
                                    .equalTo(currentDate)
                                    .where(BookIndexDynamicSqlSupport.id, isEqualTo(indexId))
                                    .build()
                                    .render(RenderingStrategy.MYBATIS3));

                    //更新小说内容表
                    bookContentMapper.update(
                            update(bookContent)
                                    .set(BookContentDynamicSqlSupport.content)
                                    .equalTo(content)
                                    .where(BookContentDynamicSqlSupport.indexId, isEqualTo(indexId))
                                    .build().render(RenderingStrategy.MYBATIS3));

                }
            }

        }
    }



}
