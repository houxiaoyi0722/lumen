package com.sang.common.domain.auth.authorization.user.entity.finder;

import com.sang.common.domain.auth.authorization.user.entity.UserGroup;
import io.ebean.Finder;
import lombok.Builder;

/**
 * 用户模块
 * 用户组
 * finder
 * hxy 2022-11-02 17:04:18
 */
@Builder
public class UserGroupFinder extends Finder<Long, UserGroup> {

    /**
    * Construct using the default EbeanServer.
    */
    public UserGroupFinder() {
        super(UserGroup.class);
    }

}

