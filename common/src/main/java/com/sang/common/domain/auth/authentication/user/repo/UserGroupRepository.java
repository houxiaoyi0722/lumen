package com.sang.common.domain.auth.authentication.user.repo;

import com.sang.common.domain.auth.authentication.user.entity.UserGroup;
import com.sang.common.domain.auth.authentication.user.entity.query.QUserGroup;
import com.sang.common.domain.auth.authentication.user.param.UserGroupQry;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户模块
 * 用户组
 * Repository
 * hxy 2022-11-02 17:04:18
 */
@Repository
public class UserGroupRepository extends BeanRepository<Long, UserGroup> {

    protected UserGroupRepository(Database server) {
        super(UserGroup.class, server);
    }

    public PagedList<UserGroup> getPage(UserGroupQry qry) {
        QUserGroup alice = QUserGroup.alias();

        return new QUserGroup()
            .select()
            .setFirstRow(qry.getStartPosition())
            .setMaxRows(qry.getEndPosition())
            .orderBy().whenCreated.desc()
            .findPagedList();
    }


    public List<UserGroup> findTop() {
        return new QUserGroup().parentId.isNull().findList();
    }
}
