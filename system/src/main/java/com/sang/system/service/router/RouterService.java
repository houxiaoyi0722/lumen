package com.sang.system.service.router;

import com.sang.common.domain.router.vo.RouterVo;

import java.util.List;

/**
 * 路由管理
 * @author hxy
 * @date 2022/1/25 16:01
 **/
public interface RouterService {

    void save(com.sang.common.domain.router.entity.Router router);

    void update(com.sang.common.domain.router.entity.Router router);

    void saveAll(List<com.sang.common.domain.router.entity.Router> routers);

    void delete(com.sang.common.domain.router.entity.Router router);

    void deleteAll(List<com.sang.common.domain.router.entity.Router> routers);

    List<RouterVo> routerTree(List<String> roleCodes);
}
