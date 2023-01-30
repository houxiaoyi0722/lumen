package com.sang.system.service.permissions;

import com.sang.common.domain.auth.authentication.permissions.entity.Permissions;
import com.sang.common.domain.auth.authentication.permissions.param.PermissionsQry;
import io.ebean.PagedList;

import java.util.List;

/**
 * 权限管理
 * 操作权限
 *
 * hxy 2022-12-13 14:11:40
 */
public interface PermissionsService {

    PagedList<Permissions> permissionsList(PermissionsQry qry);

    Permissions findOne(Long id);

    void save(Permissions permissions);

    void saveAll(List<Permissions> permissionss);

    void insert(Permissions permissions);

    void update(Permissions permissions);

    void delete(Permissions permissions);

    void deleteAll(List<Permissions> permissionss);

    List<Permissions> permissionsListByRoute(PermissionsQry qry);

    List<Permissions> permissionsListByRole(PermissionsQry qry);
}
