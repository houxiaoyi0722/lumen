package com.sang.common.domain.auth.authentication.permissions.entity.finder;

import com.sang.common.domain.auth.authentication.permissions.entity.Permissions;
import io.ebean.Finder;
import lombok.Builder;

/**
 * 权限管理
 * 操作权限
 * finder
 * hxy 2022-12-13 14:11:40
 */
@Builder
public class PermissionsFinder extends Finder<Long, Permissions> {

    /**
    * Construct using the default EbeanServer.
    */
    public PermissionsFinder() {
        super(Permissions.class);
    }

}

