/*
package com.sang.system.service.router.impl;

import com.sang.common.domain.router.dto.RouterDto;
import com.sang.common.domain.router.entity.Router;
import com.sang.system.domain.router.repo.RouterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouterServiceImplTest {

    @Mock
    private RouterRepository mockRouterRepository;

    @InjectMocks
    private RouterServiceImpl routerServiceImplUnderTest;

    @Test
    void testSave() {
        // Setup
        final Router router = new Router();

        // Run the test
        routerServiceImplUnderTest.save(router);

        // Verify the results
    }

    @Test
    void testUpdate() {
        // Setup
        final Router router = new Router();

        // Run the test
        routerServiceImplUnderTest.update(router);

        // Verify the results
    }

    @Test
    void testSaveAll() {
        // Setup
        final List<Router> routers = List.of(new Router());
        when(mockRouterRepository.saveAll(List.of(new Router()))).thenReturn(0);

        // Run the test
        routerServiceImplUnderTest.saveAll(routers);

        // Verify the results
        verify(mockRouterRepository).saveAll(List.of(new Router()));
    }

    @Test
    void testDelete() {
        // Setup
        final Router router = new Router();

        // Run the test
        routerServiceImplUnderTest.delete(router);

        // Verify the results
    }

    @Test
    void testDeleteAll() {
        // Setup
        final List<Router> routers = List.of(new Router());
        when(mockRouterRepository.deleteAll(List.of(new Router()))).thenReturn(0);

        // Run the test
        routerServiceImplUnderTest.deleteAll(routers);

        // Verify the results
        verify(mockRouterRepository).deleteAll(List.of(new Router()));
    }

    @Test
    void testRouterTree() {
        // Setup
        // Configure RouterRepository.routerListByRoleCodes(...).
        final List<RouterDto> routerDtos = List.of(new RouterDto(0L, "name", "path", "redirect", "component", "mate", "description", false, false, 0L, 0, List.of()));
        when(mockRouterRepository.routerListByRoleCodes(List.of("value"))).thenReturn(routerDtos);

        // Run the test
        final List<RouterDto> result = routerServiceImplUnderTest.routerTree();

        // Verify the results
    }

    @Test
    void testRouterTree_RouterRepositoryReturnsNoItems() {
        // Setup
        when(mockRouterRepository.routerListByRoleCodes(List.of("value"))).thenReturn(Collections.emptyList());

        // Run the test
        final List<RouterDto> result = routerServiceImplUnderTest.routerTree();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }
}
*/
