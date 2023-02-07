package com.sang.system.service.permission;

import com.sang.common.domain.auth.authorization.permission.entity.Permission;
import com.sang.common.domain.auth.authorization.permission.param.PermissionQry;
import io.ebean.PagedList;

import java.util.List;

/**
 * 权限管理
 * 操作权限
 *
 * hxy 2022-12-13 14:11:40
 */
public interface PermissionService {

    PagedList<Permission> permissionsList(PermissionQry qry);

    Permission findOne(Long id);

    void save(Permission permission);

    void saveAll(List<Permission> permissionsses);

    void insert(Permission permission);

    void update(Permission permission);

    void delete(Permission permission);

    void deleteAll(List<Permission> permissionsses);

    List<Permission> permissionsListByRoute(PermissionQry qry);

    List<Permission> permissionsListByRole(PermissionQry qry);

    void updateAll(List<Permission> updateList);
}
