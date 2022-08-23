package com.sang.system.controller.router;

import com.sang.common.domain.auth.authentication.router.dto.RouterDto;
import com.sang.common.domain.auth.authentication.router.entity.Router;
import com.sang.common.response.Result;
import com.sang.system.service.router.RouterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 路由管理
 * @author hxy
 * @date 2022/1/25 16:01
 **/
@RequestMapping("/lumen/router")
@RestController
public class RouterController {

    @Resource
    private RouterService routerService;


    /**
     * 根据当前用户返回路由列表
     * @return
     */
    @GetMapping("/routerTree")
    public Result<List<RouterDto>> routerTree() {
        return Result.ok(routerService.routerTree());
    }

    /**
     * 保存
     * @param router
     * @return
     */
    @PostMapping("/router")
    public Result<Boolean> save(@RequestBody Router router) {
        routerService.save(router);
        return Result.ok();
    }

    /**
     * 通过id更新
     * @param router
     * @return
     */
    @PutMapping("/router")
    public Result<Boolean> update(@RequestBody Router router) {
        routerService.update(router);
        return Result.ok();
    }

    /**
     * 批量保存
     * @param routers
     * @return
     */
    @PostMapping("/routers")
    public Result<Boolean> saveAll(@RequestBody List<Router> routers) {
        routerService.saveAll(routers);
        return Result.ok();
    }

    /**
     * 通过id删除
     * @param router
     * @return
     */
    @DeleteMapping("/router")
    public Result<Boolean> delete(@RequestBody Router router) {
        routerService.delete(router);
        return Result.ok();
    }

    /**
     * 批量删除
     * @param routers
     * @return
     */
    @DeleteMapping("/routers")
    public Result<Boolean> deleteAll(@RequestBody List<Router> routers) {
        routerService.deleteAll(routers);
        return Result.ok();
    }


}
