package com.sang.system.service.user;

import com.sang.common.domain.auth.authentication.user.entity.UserGroup;
import com.sang.common.domain.auth.authentication.user.param.UserGroupQry;
import io.ebean.PagedList;

import java.util.List;

/**
 * 用户模块
 * 用户组
 *
 * hxy 2022-11-02 17:04:18
 */
public interface UserGroupService {

    PagedList<UserGroup> userGroupList(UserGroupQry qry);

    UserGroup findOne(Long id);

    void save(UserGroup usergroup);

    void saveAll(List<UserGroup> usergroups);

    void insert(UserGroup usergroup);

    void update(UserGroup usergroup);

    void delete(UserGroup usergroup);

    void deleteAll(List<UserGroup> usergroups);

    List<UserGroup> findAll();

    void updateAll(List<UserGroup> userGroupList);

    List<UserGroup> findTop();
}
