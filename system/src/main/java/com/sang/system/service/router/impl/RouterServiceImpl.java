package com.sang.system.service.router.impl;

import com.sang.common.domain.router.dto.RouterDto;
import com.sang.common.domain.router.entity.Router;
import com.sang.system.domain.router.repo.RouterRepository;
import com.sang.system.service.router.RouterService;
import io.ebean.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 路由管理
 * @author hxy
 * @date 2022/1/25 16:01
 **/
@Service
public class RouterServiceImpl implements RouterService {

    @Resource
    private RouterRepository routerRepository;


    @Override
    @Transactional
    public void save(Router router) {
        router.save();
    }

    @Override
    @Transactional
    public void update(Router router) {
        router.update();
    }

    @Override
    @Transactional
    public void saveAll(List<Router> routers) {
        routerRepository.saveAll(routers);
    }

    @Override
    @Transactional
    public void delete(Router router) {
        router.delete();
    }

    @Override
    @Transactional
    public void deleteAll(List<Router> routers) {
        routerRepository.deleteAll(routers);
    }

    @Override
    public List<RouterDto> routerTree() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String authority1 = authority.getAuthority();
        }

        return null;
    }
}
