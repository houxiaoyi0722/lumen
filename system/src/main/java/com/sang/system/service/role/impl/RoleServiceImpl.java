package com.sang.system.service.role.impl;

import com.sang.common.domain.auth.authorization.role.entity.Role;
import com.sang.common.domain.auth.authorization.role.repo.RoleRepository;
import com.sang.system.service.role.RoleService;
import io.ebean.annotation.Transactional;
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

    @Override
    public List<Role> rolesByParentId(Long parentId) {
        return roleRepository.rolesByParentId(parentId);
    }

    @Override
    @Transactional
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public void update(Role role) {
        // 只更新有值的字段
        role.update();
        // 更新全部字段
//        roleRepository.update(role);
    }

    @Override
    @Transactional
    public void insert(Role role) {
        roleRepository.insert(role);
    }

    @Override
    @Transactional
    public void saveAll(List<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    @Transactional
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    @Transactional
    public void deleteAll(List<Role> roles) {
        roleRepository.deleteAll(roles);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional
    @Override
    public void updateAll(List<Role> dtoToRoleList) {
        dtoToRoleList.forEach(roleRepository::update);
    }
}
