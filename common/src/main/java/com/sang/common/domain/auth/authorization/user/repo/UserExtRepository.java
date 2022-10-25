package com.sang.common.domain.auth.authorization.user.repo;

import com.sang.common.domain.auth.authorization.user.entity.UserExt;
import com.sang.common.domain.auth.authorization.user.entity.query.QUserExt;
import com.sang.common.domain.auth.authorization.user.param.UserExtQry;
import io.ebean.BeanRepository;
import io.ebean.Database;
import io.ebean.PagedList;
import org.springframework.stereotype.Repository;

/**
 * 用户模块
 * 用户扩展信息
 * Repository
 * hxy 2022-10-24 17:02:14
 */
@Repository
public class UserExtRepository extends BeanRepository<Long, UserExt> {

    protected UserExtRepository(Database server) {
        super(UserExt.class, server);
    }

    public PagedList<UserExt> getPage(UserExtQry qry) {
        QUserExt alice = QUserExt.alias();

        return new QUserExt()
            .select()
            .setFirstRow(qry.getStartPosition())
            .setMaxRows(qry.getEndPosition())
            .orderBy().whenCreated.desc()
            .findPagedList();
    }


}
