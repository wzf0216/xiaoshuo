package com.novel.entity;

import java.util.Date;
import javax.annotation.Generated;

public class SysUser {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long userId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String username;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String name;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String password;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long deptId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String email;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String mobile;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Byte status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long userIdCreate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date gmtCreate;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date gmtModified;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long sex;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date birth;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long picId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String liveAddress;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String hobby;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String province;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String city;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String district;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getUserId() {
        return userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getUsername() {
        return username;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getName() {
        return name;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getPassword() {
        return password;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getDeptId() {
        return deptId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getEmail() {
        return email;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getMobile() {
        return mobile;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Byte getStatus() {
        return status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setStatus(Byte status) {
        this.status = status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getUserIdCreate() {
        return userIdCreate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setUserIdCreate(Long userIdCreate) {
        this.userIdCreate = userIdCreate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getGmtCreate() {
        return gmtCreate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getGmtModified() {
        return gmtModified;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getSex() {
        return sex;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setSex(Long sex) {
        this.sex = sex;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getBirth() {
        return birth;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getPicId() {
        return picId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setPicId(Long picId) {
        this.picId = picId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getLiveAddress() {
        return liveAddress;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setLiveAddress(String liveAddress) {
        this.liveAddress = liveAddress == null ? null : liveAddress.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getHobby() {
        return hobby;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setHobby(String hobby) {
        this.hobby = hobby == null ? null : hobby.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getProvince() {
        return province;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getCity() {
        return city;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getDistrict() {
        return district;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }
}