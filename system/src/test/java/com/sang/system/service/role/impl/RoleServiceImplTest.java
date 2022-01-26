package com.sang.system.service.role.impl;

import com.sang.common.domain.role.entity.Role;
import com.sang.system.domain.role.repo.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository mockRoleRepository;

    @InjectMocks
    private RoleServiceImpl roleServiceImplUnderTest;

    @Test
    void testFindTopRoles() {
        // Setup
        when(mockRoleRepository.findTopRoles()).thenReturn(List.of(new Role()));

        // Run the test
        final List<Role> result = roleServiceImplUnderTest.findTopRoles();

        // Verify the results
    }

    @Test
    void testFindTopRoles_RoleRepositoryReturnsNoItems() {
        // Setup
        when(mockRoleRepository.findTopRoles()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Role> result = roleServiceImplUnderTest.findTopRoles();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testRolesByParentId() {
        // Setup
        when(mockRoleRepository.rolesByParentId(0L)).thenReturn(List.of(new Role()));

        // Run the test
        final List<Role> result = roleServiceImplUnderTest.rolesByParentId(0L);

        // Verify the results
    }

    @Test
    void testRolesByParentId_RoleRepositoryReturnsNoItems() {
        // Setup
        when(mockRoleRepository.rolesByParentId(0L)).thenReturn(Collections.emptyList());

        // Run the test
        final List<Role> result = roleServiceImplUnderTest.rolesByParentId(0L);

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testSave() {
        // Setup
        final Role role = new Role();

        // Run the test
        roleServiceImplUnderTest.save(role);

        // Verify the results
        verify(mockRoleRepository).save(any(Role.class));
    }

    @Test
    void testUpdate() {
        // Setup
        final Role role = new Role();

        // Run the test
        roleServiceImplUnderTest.update(role);

        // Verify the results
        verify(mockRoleRepository).update(any(Role.class));
    }

    @Test
    void testInsert() {
        // Setup
        final Role role = new Role();

        // Run the test
        roleServiceImplUnderTest.insert(role);

        // Verify the results
        verify(mockRoleRepository).insert(any(Role.class));
    }

    @Test
    void testSaveAll() {
        // Setup
        final List<Role> roles = List.of(new Role());
        when(mockRoleRepository.saveAll(List.of(new Role()))).thenReturn(0);

        // Run the test
        roleServiceImplUnderTest.saveAll(roles);

        // Verify the results
        verify(mockRoleRepository).saveAll(List.of(new Role()));
    }

    @Test
    void testDelete() {
        // Setup
        final Role role = new Role();
        when(mockRoleRepository.delete(any(Role.class))).thenReturn(false);

        // Run the test
        roleServiceImplUnderTest.delete(role);

        // Verify the results
        verify(mockRoleRepository).delete(any(Role.class));
    }

    @Test
    void testDeleteAll() {
        // Setup
        final List<Role> roles = List.of(new Role());
        when(mockRoleRepository.deleteAll(List.of(new Role()))).thenReturn(0);

        // Run the test
        roleServiceImplUnderTest.deleteAll(roles);

        // Verify the results
        verify(mockRoleRepository).deleteAll(List.of(new Role()));
    }
}
