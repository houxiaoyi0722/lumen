package com.sang.system.service.user.impl;

import com.sang.common.domain.user.entity.User;
import com.sang.system.domain.user.param.UserQry;
import com.sang.system.domain.user.repo.UserRepository;
import com.sang.system.service.user.UserService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author hxy
 * @date 2022/1/7 15:09
 **/
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Resource
    private UserRepository userRepository;

    @Override
    public PagedList<User> userList(UserQry userQry) {
        return userRepository.userList(userQry);
    }

    @Override
    @Transactional
    public void save(User user) {
        user.save();
    }

    @Override
    @Transactional
    public void update(User user) {
        user.update();
    }

    @Override
    @Transactional
    public void delete(User user) {
        user.delete();
    }

    @Override
    @Transactional
    public void deleteAll(List<User> users) {
        userRepository.deleteAll(users);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserDetails> userDetails = Optional.ofNullable(userRepository.loadUserByUsername(username));

        if (!userDetails.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return userDetails.get();
    }
}
