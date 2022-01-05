package com.sang.system.service.role;

import com.sang.common.domain.role.entity.Role;
import com.sang.system.domain.role.dto.RoleDto;

import java.util.List;

/**
 * @author hxy
 * @date 2022/1/5 15:43
 **/
public interface RoleService {
    List<Role> findTopRoles();

}
