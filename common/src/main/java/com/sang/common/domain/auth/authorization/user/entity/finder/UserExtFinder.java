package com.sang.common.domain.auth.authorization.user.entity.finder;

import com.sang.common.domain.auth.authorization.user.entity.UserExt;
import io.ebean.Finder;
import lombok.Builder;

/**
 * 用户模块
 * 用户扩展信息
 * finder
 * hxy 2022-10-24 17:02:14
 */
@Builder
public class UserExtFinder extends Finder<Long, UserExt> {

    /**
    * Construct using the default EbeanServer.
    */
    public UserExtFinder() {
        super(UserExt.class);
    }

}

