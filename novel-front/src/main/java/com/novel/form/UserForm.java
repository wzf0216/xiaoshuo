package com.novel.form;

import lombok.Data;

import javax.annotation.Generated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserForm {
    //只能作用在String上，不能为null，而且调用trim()后，长度必须大于0
    @NotBlank(message="手机号不能为空！")
    //通过正则表达式式检验用户名
    @Pattern(regexp="^1[3|4|5|6|7|8|9][0-9]{9}$",message="手机号格式不正确！")
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String username;

    @NotBlank(message="密码不能为空！")
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String password;

}
