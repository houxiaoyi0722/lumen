package com.sang.system.service.user.impl;

import com.sang.common.domain.user.entity.User;
import com.sang.system.domain.user.param.UserParam;
import com.sang.system.domain.user.repo.UserRepository;
import com.sang.system.service.user.UserService;
import io.ebean.PagedList;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hxy
 * @date 2022/1/7 15:09
 **/
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public PagedList<User> userList(UserParam userParam) {
        return userRepository.userList(userParam);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.update(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public void deleteAll(List<User> users) {
        userRepository.deleteAll(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.loadUserByUsername(username);
    }
}
