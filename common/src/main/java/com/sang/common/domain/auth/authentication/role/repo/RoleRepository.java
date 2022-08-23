package com.sang.common.domain.auth.authentication.role.repo;

import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.domain.auth.authentication.role.entity.query.QRole;
import io.ebean.BeanRepository;
import io.ebean.DB;
import io.ebean.Database;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hxy
 * @date 2022/1/5 15:36
 **/
@Repository
public class RoleRepository extends BeanRepository<Long, Role> {

    public RoleRepository(Database server) {super(Role.class, server);}

    public List<Role> findTopRoles() {
        return new QRole()
                .parentId.isNull()
                .orderBy().whenCreated.asc()
                .findList();
    }

    public List<Role> rolesByParentId(Long parentId) {
        if (parentId == null) {
            return findTopRoles();
        }

        return DB.find(Role.class).where().eq("PARENT_ID",parentId).orderBy().asc("id").findList();
    }
}
