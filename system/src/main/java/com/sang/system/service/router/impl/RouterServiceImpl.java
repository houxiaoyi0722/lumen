package com.sang.system.service.router.impl;

import com.sang.common.domain.router.entity.Router;
import com.sang.common.domain.router.mapper.RouterMapper;
import com.sang.common.domain.router.repo.RouterRepository;
import com.sang.common.domain.router.vo.RouterVo;
import com.sang.system.service.router.RouterService;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 路由管理
 * @author hxy
 * @date 2022/1/25 16:01
 **/
@Service
@Slf4j
public class RouterServiceImpl implements RouterService {

    private final RouterMapper routerMapper = RouterMapper.mapper;

    @Resource
    private RouterRepository routerRepository;


    @Override
    @Transactional
    public void save(com.sang.common.domain.router.entity.Router router) {
        router.save();
    }

    @Override
    @Transactional
    public void update(com.sang.common.domain.router.entity.Router router) {
        router.update();
    }

    @Override
    @Transactional
    public void saveAll(List<com.sang.common.domain.router.entity.Router> routers) {
        routerRepository.saveAll(routers);
    }

    @Override
    @Transactional
    public void delete(com.sang.common.domain.router.entity.Router router) {
        router.delete();
    }

    @Override
    @Transactional
    public void deleteAll(List<com.sang.common.domain.router.entity.Router> routers) {
        routerRepository.deleteAll(routers);
    }

    @Override
    public List<RouterVo> routerTree() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roleCodes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        List<RouterVo> routerVos = routerMapper.routerToVoList(routerRepository.routerListByRoleCodes(roleCodes));

        return Router.getRootNodeRouterTree(routerVos);
    }
}
