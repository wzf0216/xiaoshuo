package com.novel.service;

import com.novel.entity.SysUser;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface SysUserService {
SysUser login(String username);
    SysUser get(Long id);
    List<SysUser> list();
    Long count();
    int save(SysUser user);
    int update(SysUser user);
    int remove(Long userId);
    int batchRemove(Long[] userIds);

    Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception;
}
