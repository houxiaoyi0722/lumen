package com.sang.common.domain.auth.authentication.permissions.repo;

import com.sang.common.domain.auth.authentication.permissions.entity.Permissions;
import com.sang.common.domain.auth.authentication.permissions.entity.query.QPermissions;
import com.sang.common.domain.auth.authentication.permissions.param.PermissionsQry;
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
public class PermissionsRepository extends BeanRepository<Long, Permissions> {

    protected PermissionsRepository(Database server) {
        super(Permissions.class, server);
    }

    public PagedList<Permissions> getPage(PermissionsQry qry) {
        QPermissions alice = QPermissions.alias();

        return new QPermissions()
            .select()
            .setFirstRow(qry.getStartPosition())
            .setMaxRows(qry.getEndPosition())
            .orderBy().whenCreated.desc()
            .findPagedList();
    }


    public List<Permissions> permissionsListByRoute(PermissionsQry qry) {
        return new QPermissions().router.id.eq(qry.getRouter().getId()).findList();
    }

    public List<Permissions> permissionsListByRole(PermissionsQry qry) {
        return new QPermissions().roles.id.eq(qry.getRole().getId()).findList();
    }
}
