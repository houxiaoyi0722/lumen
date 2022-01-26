package com.sang.system.service.router.impl;

import cn.hutool.core.bean.BeanUtil;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        List<String> roleCodes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        List<RouterDto> routers = routerRepository.routerListByRoleCodes(roleCodes);

        return Router.getRootNodeRouterTree(routers);
    }
}
