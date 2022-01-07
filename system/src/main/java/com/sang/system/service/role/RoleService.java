package com.sang.system.service.role;

import com.sang.common.domain.role.entity.Role;

import java.util.List;

/**
 * @author hxy
 * @date 2022/1/5 15:43
 **/
public interface RoleService {
    List<Role> findTopRoles();

    List<Role> rolesByParentId(Long parentId);

    void save(Role role);

    void update(Role role);

    void insert(Role role);

    void saveAll(List<Role> roles);

    void delete(Role role);

    void deleteAll(List<Role> roles);
}
