package com.sang.common.domain.auth.authorization.permission.entity.finder;

import com.sang.common.domain.auth.authorization.permission.entity.Permission;
import io.ebean.Finder;
import lombok.Builder;

/**
 * 权限管理
 * 操作权限
 * finder
 * hxy 2022-12-13 14:11:40
 */
@Builder
public class PermissionFinder extends Finder<Long, Permission> {

    /**
    * Construct using the default EbeanServer.
    */
    public PermissionFinder() {
        super(Permission.class);
    }

}

