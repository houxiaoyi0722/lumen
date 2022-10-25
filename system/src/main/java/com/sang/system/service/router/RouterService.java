package com.sang.system.service.router;

import com.sang.common.domain.router.entity.Router;
import com.sang.common.domain.router.vo.RouterVo;

import java.util.List;

/**
 * 路由管理
 * @author hxy
 * @date 2022/1/25 16:01
 **/
public interface RouterService {

    void save(Router router);

    void update(Router router);

    void saveAll(List<Router> routers);

    void delete(Router router);

    void deleteAll(List<Router> routers);

    List<RouterVo> routerTree(List<String> roleCodes);

    List<Router> routerList();
}
