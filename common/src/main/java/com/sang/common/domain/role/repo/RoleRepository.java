package com.sang.common.domain.role.repo;

import com.sang.common.domain.role.entity.Role;
import com.sang.common.domain.role.entity.query.QRole;
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
        return new QRole().parentId.isEmpty().orderBy().id.asc().findList();
    }

    public List<Role> rolesByParentId(Long parentId) {
        if (parentId == null) {
            return findTopRoles();
        }

        return DB.find(Role.class).where().eq("PARENT_ID",parentId).orderBy().asc("id").findList();
    }
}
