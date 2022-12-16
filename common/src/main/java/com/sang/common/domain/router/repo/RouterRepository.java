package com.sang.common.domain.router.repo;

import cn.hutool.core.collection.CollectionUtil;
import com.sang.common.domain.router.entity.Router;
import com.sang.common.domain.router.entity.query.QRouter;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hxy
 * @date 2022/1/25 16:04
 **/
@Repository
public class RouterRepository extends BeanRepository<Long, Router> {

    protected RouterRepository(Database server) {
        super(Router.class, server);
    }

    public static final QRouter router = QRouter.alias();

    public List<Router> routerListByRoleCodes(List<String> roleCodes) {

        QRouter select = new QRouter().select(
                router.name, router.path, router.redirect,
                router.component, router.mate, router.description,
                router.hidden, router.alwaysShow, router.parentId,
                router.orderBy
        ).orderBy().orderBy.asc();
        if (CollectionUtil.isNotEmpty(roleCodes)) {
            select = select.roles.roleCode.in(roleCodes);
        }

        return select.findList();
    }

    public List<Router> routerByParentId(Long parentId, String roleCode) {
        QRouter select = new QRouter().select(router.id, router.name, router.mate).orderBy().orderBy.asc();
        select = (parentId == null) ?select.parentId.isNull() : select.parentId.id.eq(parentId);
        select.roles.roleCode.eq(roleCode);

        return select.findList();
    }
}
