package com.sang.system.service.user;

import com.sang.common.domain.auth.authorization.user.entity.UserExt;
import com.sang.common.domain.auth.authorization.user.param.UserExtQry;
import io.ebean.PagedList;

import java.util.List;

/**
 * 用户模块
 * 用户扩展信息
 * 
 * hxy 2022-10-24 17:02:14
 */
public interface UserExtService {

    PagedList<UserExt> userExtList(UserExtQry qry);

    UserExt findOne(Long id);

    void save(UserExt userext);

    void saveAll(List<UserExt> userExts);

    void insert(UserExt userext);

    void update(UserExt userext);

    void delete(UserExt userext);

    void deleteAll(List<UserExt> userExts);

    UserExt findByUserId(Long userId);
}