package com.sang.system.service.user;

import com.sang.common.domain.auth.authentication.user.entity.User;
import com.sang.common.domain.auth.authentication.user.param.UserQry;
import io.ebean.PagedList;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    PagedList<User> userList(UserQry userQry);

    void save(User user);

    void update(User user);

    void delete(User user);

    void deleteAll(List<User> users);

    UserDetails loadUserByUsername(String username);

    void resetPassWord(User dtoToUser);

    User userinfo(String username);
}
