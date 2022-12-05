package com.sang.common.domain.auth.authorization.user.param;

import cn.hutool.db.Page;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hxy
 * @date 2022/1/7 15:18
 **/
@Getter
@Setter
@AllArgsConstructor
public class UserQry extends Page {

    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 角色id
     */
    private Long roles;

    /**
     * 用户组id
     */
    private Long userGroup;
}
