package com.sang.system.service.permission.impl;

import com.sang.common.domain.auth.authorization.permission.entity.Permission;
import com.sang.common.domain.auth.authorization.permission.param.PermissionQry;
import com.sang.common.domain.auth.authorization.permission.repo.PermissionRepository;
import com.sang.system.service.permission.PermissionService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理
 * 操作权限
 *
 * hxy 2022-12-13 14:11:40
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionRepository repository;

    @Override
    public PagedList<Permission> permissionsList(PermissionQry qry) {
        return repository.getPage(qry);
    }

    @Override
    public Permission findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(Permission permission) {
        permission.save();
    }

    @Override
    @Transactional
    public void insert(Permission permission) {
        permission.insert();
    }

    @Override
    @Transactional
    public void update(Permission permission) {
        permission.update();
    }

    @Override
    @Transactional
    public void delete(Permission permission) {
        repository.delete(permission);
    }

    @Override
    @Transactional
    public void saveAll(List<Permission> permissions) {
        repository.saveAll(permissions);
    }

    @Override
    @Transactional
    public void deleteAll(List<Permission> permissions) {
        repository.deleteAll(permissions);
    }

    @Override
    public List<Permission> permissionsListByRoute(PermissionQry qry) {
        return repository.permissionsListByRoute(qry);
    }

    @Override
    public List<Permission> permissionsListByRole(PermissionQry qry) {
        return repository.permissionsListByRole(qry);
    }

    @Override
    public void updateAll(List<Permission> updateList) {
        updateList.forEach(Permission::update);
    }
}
