package com.sang.common.domain.router.repo;

import cn.hutool.core.collection.CollectionUtil;
import com.sang.common.domain.role.entity.query.QRole;
import com.sang.common.domain.router.dto.RouterDto;
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


    public List<RouterDto> routerListByRoleCodes(List<String> roleCodes) {
        QRouter router = QRouter.alias();

        QRouter select = new QRouter().select(
                router.name, router.path, router.redirect,
                router.component, router.mate, router.description,
                router.hidden, router.alwaysShow, router.parentId.id,
                router.orderBy
        );
        if (CollectionUtil.isNotEmpty(roleCodes)) {
            select = select.roles.roleCode.in(roleCodes);
        }

        return select.asDto(RouterDto.class).findList();
    }
}
