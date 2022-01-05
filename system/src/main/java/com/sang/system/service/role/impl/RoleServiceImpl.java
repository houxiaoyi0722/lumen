package com.sang.system.service.role.impl;

import com.sang.common.domain.role.entity.Role;
import com.sang.system.domain.role.dto.RoleDto;
import com.sang.system.domain.role.repo.RoleRepository;
import com.sang.system.service.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hxy
 * @date 2022/1/5 15:46
 **/
@Service @Slf4j
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    /**
     * 查询顶级角色
     * @return 角色顶级角色列表
     */
    @Override
    public List<Role> findTopRoles() {
        return roleRepository.findTopRoles();
    }
}
