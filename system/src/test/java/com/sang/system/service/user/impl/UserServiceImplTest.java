/*
package com.sang.system.service.user.impl;

import com.sang.common.domain.role.entity.Role;
import com.sang.common.domain.user.entity.User;
import com.sang.common.domain.user.entity.UserGroup;
import com.sang.common.domain.user.param.UserQry;
import com.sang.common.domain.user.repo.UserRepository;
import io.ebean.PagedList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserServiceImpl userServiceImplUnderTest;

    @Test
    void testUserList() {
        // Setup
        final UserQry userQry = new UserQry();
        when(mockUserRepository.userList(any(UserQry.class))).thenReturn(PagedList.emptyList());

        // Run the test
        final PagedList<User> result = userServiceImplUnderTest.userList(userQry);

        // Verify the results
    }

    @Test
    void testUserList_UserRepositoryReturnsNoItem() {
        // Setup
        final UserQry userQry = new UserQry();
        when(mockUserRepository.userList(any(UserQry.class))).thenReturn(PagedList.emptyList());

        // Run the test
        final PagedList<User> result = userServiceImplUnderTest.userList(userQry);

        // Verify the results
    }

    @Test
    void testSave() {
        // Setup
        final User user = new User("name", "userName", "password", "phone", "mobilePhone", "address", "email", false, false, false, false, List.of(new Role()), new UserGroup());

        // Run the test
        userServiceImplUnderTest.save(user);

        // Verify the results
    }

    @Test
    void testUpdate() {
        // Setup
        final User user = new User("name", "userName", "password", "phone", "mobilePhone", "address", "email", false, false, false, false, List.of(new Role()), new UserGroup());

        // Run the test
        userServiceImplUnderTest.update(user);

        // Verify the results
    }

    @Test
    void testDelete() {
        // Setup
        final User user = new User("name", "userName", "password", "phone", "mobilePhone", "address", "email", false, false, false, false, List.of(new Role()), new UserGroup());

        // Run the test
        userServiceImplUnderTest.delete(user);

        // Verify the results
    }

    @Test
    void testDeleteAll() {
        // Setup
        final List<User> users = List.of(new User("name", "userName", "password", "phone", "mobilePhone", "address", "email", false, false, false, false, List.of(new Role()), new UserGroup()));
        when(mockUserRepository.deleteAll(List.of(new User("name", "userName", "password", "phone", "mobilePhone", "address", "email", false, false, false, false, List.of(new Role()), new UserGroup())))).thenReturn(0);

        // Run the test
        userServiceImplUnderTest.deleteAll(users);

        // Verify the results
        verify(mockUserRepository).deleteAll(List.of(new User("name", "userName", "password", "phone", "mobilePhone", "address", "email", false, false, false, false, List.of(new Role()), new UserGroup())));
    }

    @Test
    void testLoadUserByUsername() {
        // Setup
        when(mockUserRepository.loadUserByUsername("username")).thenReturn(null);

        // Run the test
        final UserDetails result = userServiceImplUnderTest.loadUserByUsername("username");

        // Verify the results
    }

    @Test
    void testLoadUserByUsername_ThrowsUsernameNotFoundException() {
        // Setup
        when(mockUserRepository.loadUserByUsername("username")).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> userServiceImplUnderTest.loadUserByUsername("username")).isInstanceOf(UsernameNotFoundException.class);
    }
}
*/
