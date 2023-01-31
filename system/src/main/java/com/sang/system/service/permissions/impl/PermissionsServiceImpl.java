package com.sang.system.service.permissions.impl;

import com.sang.common.domain.auth.authentication.permissions.entity.Permissions;
import com.sang.common.domain.auth.authentication.permissions.param.PermissionsQry;
import com.sang.common.domain.auth.authentication.permissions.repo.PermissionsRepository;
import com.sang.common.domain.router.dto.RouterDto;
import com.sang.system.service.permissions.PermissionsService;
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
public class PermissionsServiceImpl implements PermissionsService {

    @Resource
    private PermissionsRepository repository;

    @Override
    public PagedList<Permissions> permissionsList(PermissionsQry qry) {
        return repository.getPage(qry);
    }

    @Override
    public Permissions findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(Permissions permissions) {
        permissions.save();
    }

    @Override
    @Transactional
    public void insert(Permissions permissions) {
        permissions.insert();
    }

    @Override
    @Transactional
    public void update(Permissions permissions) {
        permissions.update();
    }

    @Override
    @Transactional
    public void delete(Permissions permissions) {
        repository.delete(permissions);
    }

    @Override
    @Transactional
    public void saveAll(List<Permissions> permissions) {
        repository.saveAll(permissions);
    }

    @Override
    @Transactional
    public void deleteAll(List<Permissions> permissions) {
        repository.deleteAll(permissions);
    }

    @Override
    public List<Permissions> permissionsListByRoute(PermissionsQry qry) {
        return repository.permissionsListByRoute(qry);
    }

    @Override
    public List<Permissions> permissionsListByRole(PermissionsQry qry) {
        return repository.permissionsListByRole(qry);
    }

    @Override
    public void updateAll(List<Permissions> updateList) {
        updateList.forEach(Permissions::update);
    }
}
