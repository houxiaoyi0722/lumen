package com.sang.common.domain.auth.authorization.permission.repo;

import com.sang.common.domain.auth.authorization.permission.entity.Permission;
import com.sang.common.domain.auth.authorization.permission.entity.query.QPermission;
import com.sang.common.domain.auth.authorization.permission.param.PermissionQry;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限管理
 * 操作权限
 * Repository
 * hxy 2022-12-13 14:11:40
 */
@Repository
public class PermissionRepository extends BeanRepository<Long, Permission> {

    protected PermissionRepository(Database server) {
        super(Permission.class, server);
    }

    public PagedList<Permission> getPage(PermissionQry qry) {
        QPermission alice = QPermission.alias();

        return new QPermission()
            .select()
            .setFirstRow(qry.getStartPosition())
            .setMaxRows(qry.getEndPosition())
            .orderBy().whenCreated.desc()
            .findPagedList();
    }


    public List<Permission> permissionsListByRoute(PermissionQry qry) {
        return new QPermission().router.id.eq(qry.getRouter().getId()).findList();
    }

    public List<Permission> permissionsListByRole(PermissionQry qry) {
        return new QPermission().roles.id.eq(qry.getRole().getId()).findList();
    }
}
