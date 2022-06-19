package com.novel.service;



import com.novel.entity.FriendLink;

import java.util.List;


public interface FriendLinkService {

    /**
     * 查询首页友情链接
     * @return 集合
     * */
    List<FriendLink> listIndexLink();

}
