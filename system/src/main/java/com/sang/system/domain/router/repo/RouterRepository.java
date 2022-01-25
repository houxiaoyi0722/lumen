package com.sang.system.domain.router.repo;

import com.sang.common.domain.role.entity.Role;
import com.sang.common.domain.router.entity.Router;
import io.ebean.BeanRepository;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

/**
 * @author hxy
 * @date 2022/1/25 16:04
 **/
@Repository
public class RouterRepository extends BeanRepository<Long, Router> {

    protected RouterRepository(Database server) {
        super(Router.class, server);
    }



}
