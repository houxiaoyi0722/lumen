package com.sang.system.service.router.impl;


import com.sang.common.domain.router.entity.Router;
import com.sang.common.domain.router.mapper.RouterMapper;
import com.sang.common.domain.router.repo.RouterRepository;
import com.sang.common.domain.router.vo.RouterVo;
import com.sang.system.service.router.RouterService;
import io.ebean.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 路由管理
 * @author hxy 2022/1/25 16:01
 **/
@Service
@Slf4j
public class RouterServiceImpl implements RouterService {

    private final RouterMapper routerMapper = RouterMapper.mapper;

    @Resource
    private RouterRepository routerRepository;

    @CacheEvict(value = "routerTree", key = "'*'")
    @Override
    @Transactional
    public void save(Router router) {
        router.save();
    }

    @CacheEvict(value = "routerTree", key = "'*'")
    @Override
    @Transactional
    public void update(Router router) {
        router.update();
    }

    @CacheEvict(value = "routerTree", key = "'*'")
    @Override
    @Transactional
    public void saveAll(List<Router> routers) {
        routerRepository.saveAll(routers);
    }

    @CacheEvict(value = "routerTree", key = "'*'")
    @Override
    @Transactional
    public void delete(Router router) {
        router.delete();
    }

    @CacheEvict(value = "routerTree", key = "'*'")
    @Override
    @Transactional
    public void deleteAll(List<Router> routers) {
        routerRepository.deleteAll(routers);
    }

    @Cacheable(value = "routerTree",key = "#roleCodes")
    @Override
    public List<RouterVo> routerTree(List<String> roleCodes) {

        List<RouterVo> routerVos = routerMapper.routerToVoList(routerRepository.routerListByRoleCodes(roleCodes));

        return Router.getRootNodeRouterTree(routerVos);
    }

    @Override
    public List<Router> routerList() {
        return routerRepository.findAll();
    }

    @CacheEvict(value = "routerTree", key = "'*'")
    @Override
    public void updateAll(List<Router> routers) {
        routers.forEach(routerRepository::update);
    }
}
