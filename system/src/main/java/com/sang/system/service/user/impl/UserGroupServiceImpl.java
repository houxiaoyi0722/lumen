package com.sang.system.service.user.impl;

import com.sang.common.domain.auth.authorization.user.entity.UserGroup;
import com.sang.common.domain.auth.authorization.user.param.UserGroupQry;
import com.sang.common.domain.auth.authorization.user.repo.UserGroupRepository;
import com.sang.system.service.user.UserGroupService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户模块
 * 用户组
 * 
 * hxy 2022-11-02 17:04:18
 */
@Slf4j
@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Resource
    private UserGroupRepository repository;

    @Override
    public PagedList<UserGroup> userGroupList(UserGroupQry qry) {
        return repository.getPage(qry);
    }

    @Override
    public UserGroup findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(UserGroup userGroup) {
        userGroup.save();
    }

    @Override
    @Transactional
    public void insert(UserGroup userGroup) {
        userGroup.insert();
    }

    @Override
    @Transactional
    public void update(UserGroup userGroup) {
        userGroup.update();
    }

    @Override
    @Transactional
    public void delete(UserGroup userGroup) {
        repository.delete(userGroup);
    }

    @Override
    @Transactional
    public void saveAll(List<UserGroup> UserGroups) {
        repository.saveAll(UserGroups);
    }

    @Override
    @Transactional
    public void deleteAll(List<UserGroup> userGroups) {
        repository.deleteAll(userGroups);
    }

    @Override
    public List<UserGroup> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public void updateAll(List<UserGroup> userGroupList) {
        userGroupList.forEach(repository::update);
    }

    @Override
    public List<UserGroup> findTop() {
        return repository.findTop();
    }
}