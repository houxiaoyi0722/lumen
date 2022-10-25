package com.sang.system.controller.router;

import com.sang.common.domain.router.dto.RouterDto;
import com.sang.common.domain.router.entity.Router;
import com.sang.common.domain.router.mapper.RouterMapper;
import com.sang.common.domain.router.vo.RouterVo;
import com.sang.common.response.Result;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.system.service.router.RouterService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 路由管理
 * @author hxy
 * @date 2022/1/25 16:01
 **/
@Validated
@RequestMapping("/lumen/router")
@RestController
public class RouterController {

    private final RouterMapper routerMapper = RouterMapper.mapper;

    @Resource
    private RouterService routerService;


    /**
     * 根据当前用户返回路由列表
     * @return
     */
    @GetMapping("/routerTree")
    public Result<List<RouterVo>> routerTree() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roleCodes = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return Result.ok(routerService.routerTree(roleCodes));
    }

    /**
     * 返回路由列表
     * @return
     */
    @GetMapping("/allRouterTree")
    public Result<List<RouterVo>> allRouterTree() {
        return Result.ok(routerService.routerTree(Collections.emptyList()));
    }

    /**
     * 返回路由列表
     * @return
     */
    @GetMapping("/routers")
    public Result<List<RouterVo>> routers() {
        return Result.ok(routerMapper.routerToVoList(routerService.routerList()));
    }

    /**
     * 通过id更新
     * @param router
     * @return
     */
    @PutMapping("/router")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) RouterDto router) {
        routerService.update(routerMapper.dtoToRouter(router));
        return Result.ok();
    }

    /**
     * 保存
     * @param router
     * @return
     */
    @PostMapping("/router")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) RouterDto router) {
        routerService.save(routerMapper.dtoToRouter(router));
        return Result.ok();
    }

    /**
     * 批量保存
     * @param routers
     * @return
     */
    @PostMapping("/routers")
    public Result<Boolean> saveAll(@RequestBody @Validated(Create.class) List<RouterDto> routers) {
        routerService.saveAll(routerMapper.dtoToRouterList(routers));
        return Result.ok();
    }

    /**
     * 通过id删除
     * @param router
     * @return
     */
    @DeleteMapping("/router")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) RouterDto router) {
        routerService.delete(routerMapper.dtoToRouter(router));
        return Result.ok();
    }

    /**
     * 批量删除
     * @param routers
     * @return
     */
    @DeleteMapping("/routers")
    public Result<Boolean> deleteAll(@RequestBody @Validated(Delete.class) List<RouterDto> routers) {
        routerService.deleteAll(routerMapper.dtoToRouterList(routers));
        return Result.ok();
    }


}
