package com.sang.system.service.user.impl;

import com.sang.common.domain.auth.authentication.user.entity.UserExt;
import com.sang.common.domain.auth.authentication.user.param.UserExtQry;
import com.sang.common.domain.auth.authentication.user.repo.UserExtRepository;
import com.sang.system.service.user.UserExtService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户模块
 * 用户扩展信息
 *
 * hxy 2022-10-24 17:02:14
 */
@Slf4j
@Service
public class UserExtServiceImpl implements UserExtService {

    @Resource
    private UserExtRepository repository;

    @Override
    public PagedList<UserExt> userExtList(UserExtQry qry) {
        return repository.getPage(qry);
    }

    @Override
    public UserExt findOne(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public void save(UserExt userext) {
        userext.save();
    }

    @Override
    @Transactional
    public void insert(UserExt userext) {
        userext.insert();
    }

    @Override
    @Transactional
    public void update(UserExt userext) {
        userext.update();
    }

    @Override
    @Transactional
    public void delete(UserExt userext) {
        repository.delete(userext);
    }

    @Override
    @Transactional
    public void saveAll(List<UserExt> UserExts) {
        repository.saveAll(UserExts);
    }

    @Override
    @Transactional
    public void deleteAll(List<UserExt> userExts) {
        repository.deleteAll(userExts);
    }

    @Override
    public UserExt findByUserId(Long userId) {
        return repository.findByUserId(userId);
    }
}
