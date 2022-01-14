package com.sang.system.service.user;

import com.sang.common.domain.user.entity.User;
import com.sang.system.domain.user.param.UserQry;
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
}
