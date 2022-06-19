package com.novel.service.impl;

import com.github.pagehelper.PageHelper;
import com.novel.core.utils.MD5Util;
import com.novel.entity.SysUser;
import com.novel.mapper.SysUserDynamicSqlSupport;
import com.novel.mapper.SysUserMapper;
import com.novel.service.SysUserService;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.Charsets;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.render.RenderingStrategies;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.novel.mapper.SysUserDynamicSqlSupport.*;
import static com.novel.mapper.SysUserDynamicSqlSupport.username;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements SysUserService {
    private final SysUserMapper sysUserMapper;

    @Override
    public SysUser login(String username) {
        SelectStatementProvider selectStatement = select(userId, SysUserDynamicSqlSupport.username, name, password, deptId, email, mobile, status, userIdCreate, gmtCreate, gmtModified, sex, birth, picId, liveAddress, hobby, province, city, district)
                .from(sysUser)
                .where(SysUserDynamicSqlSupport.username, isEqualTo(username))
                .build()
                .render(RenderingStrategies.MYBATIS3);

        return sysUserMapper.selectMany(selectStatement).get(0);
    }

    @Override
    public SysUser get(Long id) {
        SelectStatementProvider selectStatement = select(userId, SysUserDynamicSqlSupport.username, name, password, deptId, email, mobile, status, userIdCreate, gmtCreate, gmtModified, sex, birth, picId, liveAddress, hobby, province, city, district)
                .from(sysUser)
                .where(userId, isEqualTo(id))
                .build()
                .render(RenderingStrategies.MYBATIS3);


        return sysUserMapper.selectMany(selectStatement).get(0);

    }

    @Override
    public List<SysUser> list() {


        SelectStatementProvider selectStatement = select(userId, username, name, password, deptId, email, mobile, status, userIdCreate, gmtCreate, gmtModified, sex, birth, picId, liveAddress, hobby, province, city, district)
                .from(sysUser)
                .orderBy(userId.descending())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return sysUserMapper.selectMany(selectStatement);
    }

    @Override
    public Long count() {
        SelectStatementProvider selectStatement = select(SqlBuilder.count())
                .from(sysUser)
                .where(userId, isNotNull())
                .build()
                .render(RenderingStrategies.MYBATIS3);
        return sysUserMapper.count(selectStatement);
    }

    @Override
    public int save(SysUser user) {

        return sysUserMapper.insert(user);
    }

    @Override
    public int update(SysUser user) {
        return sysUserMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int remove(Long userId) {
        return sysUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public int batchRemove(Long[] userIds) {
        int num = 0;
        for (int i = 0; i < userIds.length; i++) {
            sysUserMapper.deleteByPrimaryKey(userIds[i]);
            num++;
        }
        return num;
    }


    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
        return null;
    }
}
