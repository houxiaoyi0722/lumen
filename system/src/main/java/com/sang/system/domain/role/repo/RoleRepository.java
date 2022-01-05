package com.sang.system.domain.role.repo;

import com.sang.common.domain.base.entity.repo.BeanRepository;
import com.sang.common.domain.role.entity.Role;
import com.sang.common.domain.role.entity.query.QRole;
import io.ebean.Database;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
        return new QRole().parentId.isEmpty().findList();
    }
}
