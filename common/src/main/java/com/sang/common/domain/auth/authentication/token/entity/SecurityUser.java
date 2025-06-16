package com.sang.common.domain.auth.authentication.token.entity;

import com.sang.common.domain.auth.authorization.role.entity.Role;
import com.sang.common.domain.base.entity.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author hxy
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class SecurityUser extends BaseModel  {

    /**
     * 姓名
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 电话
     */
    private String phone;
    /**
     * 移动电话
     */
    private String mobilePhone;
    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 角色
     */
    private List<Role> roles;


}
