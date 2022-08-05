package com.sang.common.domain.auth.authorization.token.entity;

import com.sang.common.domain.base.entity.BaseModel;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import lombok.*;

import java.util.List;

/**
 * @author hxy
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SecurityUser extends BaseModel  {

    /**
     * 姓名
     */
    private String name;
    /**
     * 用户名
     */
    private String userName;
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
