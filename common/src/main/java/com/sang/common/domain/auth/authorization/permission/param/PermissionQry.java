package com.sang.common.domain.auth.authorization.permission.param;

import cn.hutool.db.Page;
import com.sang.common.domain.auth.authorization.role.entity.Role;
import com.sang.common.domain.router.entity.Router;
import lombok.*;

/**
 * 分页查询对象
 * 权限管理
 * 操作权限
 * 查询参数对象
 * hxy 2022-12-13 14:11:40
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionQry extends Page {

    private Router router;
    private Role role;

}
