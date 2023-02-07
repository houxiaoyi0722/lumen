package com.sang.system.controller.permission;

import cn.hutool.core.collection.CollUtil;
import com.sang.common.domain.auth.authorization.permission.dto.PermissionDto;
import com.sang.common.domain.auth.authorization.permission.entity.Permission;
import com.sang.common.domain.auth.authorization.permission.mapper.PermissionMapper;
import com.sang.common.domain.auth.authorization.permission.param.PermissionQry;
import com.sang.common.domain.router.dto.TableDataDto;
import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.system.service.permission.PermissionService;
import io.ebean.PagedList;
import io.ebean.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限管理
 * 操作权限
 *
 * hxy 2022-12-13 14:11:40
 */
@Validated
@RestController
@RequestMapping("/lumen/permissions")
public class PermissionsController {

    private final PermissionMapper permissionMapper = PermissionMapper.mapper;

    @Resource
    private PermissionService permissionService;

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/permission/qry")
    public PageResult<PermissionDto> list(@RequestBody PermissionQry qry) {
        PagedList<Permission> pagedList = permissionService.permissionsList(qry);
        // 查询全部字段时可不转换直接给pagedList
        return PageResult.ok(permissionMapper.permissionsToDtoList(pagedList.getList()), pagedList);
    }

    /**
     * 查询路由关联的权限列表
     *
     * @param qry
     * @return
     */
    @PostMapping("/permission/router")
    public Result<List<Permission>> permissionsListByRoute(@RequestBody PermissionQry qry) {
        List<Permission> list = permissionService.permissionsListByRoute(qry);
        return Result.ok(list);
    }

    /**
     * 查询角色关联的权限列表
     *
     * @param qry
     * @return
     */
    @PostMapping("/permission/role")
    public Result<List<Permission>> permissionsListByRole(@RequestBody PermissionQry qry) {
        List<Permission> list = permissionService.permissionsListByRole(qry);
        return Result.ok(list);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/permissions")
    public Result<Permission> findOne(@RequestParam("id") Long id) {
        return Result.ok(permissionService.findOne(id));
    }

    /**
     * 保存
     *
     * @param permissions
     * @return
     */
    @PostMapping("/permissions")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) PermissionDto permissions) {
        permissionService.save(permissionMapper.dtoToPermissions(permissions));
        return Result.ok();
    }

    /**
    * 批量保存
    * @param permissions
    * @return
    */
    @PostMapping("/permission")
    public Result<Boolean> saveAll(@RequestBody @Validated(Create.class) List<PermissionDto> permissions) {
        permissionService.saveAll(permissionMapper.dtoToPermissionsList(permissions));
        return Result.ok();
    }

    /**
     * 通过id更新
     *
     * @param permissions
     * @return
     */
    @PutMapping("/permissions")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) PermissionDto permissions) {
        permissionService.update(permissionMapper.dtoToPermissions(permissions));
        return Result.ok();
    }

    /**
     * 通过id删除
     *
     * @param permissions
     * @return
     */
    @DeleteMapping("/permissions")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) PermissionDto permissions) {
        permissionService.delete(permissionMapper.dtoToPermissions(permissions));
        return Result.ok();
    }

    /**
     * 列表批量删除
     *
     * @param permissions
     * @return
     */
    @DeleteMapping("/permission")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<PermissionDto> permissions) {
        permissionService.deleteAll(permissionMapper.dtoToPermissionsList(permissions));
        return Result.ok();
    }

    /**
     * 批量保存/更新/删除数据
     * @param tableDataDto
     * @return
     */
    @Transactional
    @PostMapping("/table/permission")
    public Result<Boolean> routerUpdate(@RequestBody @Validated TableDataDto<PermissionDto> tableDataDto) {

        if (CollUtil.isNotEmpty(tableDataDto.getInsertList())) {
            permissionService.saveAll(permissionMapper.dtoToPermissionsList(tableDataDto.getInsertList()));
        }
        if (CollUtil.isNotEmpty(tableDataDto.getUpdateList())) {
            permissionService.updateAll(permissionMapper.dtoToPermissionsList(tableDataDto.getUpdateList()));
        }
        if (CollUtil.isNotEmpty(tableDataDto.getRemoveList())) {
            permissionService.deleteAll(permissionMapper.dtoToPermissionsList(tableDataDto.getRemoveList()));
        }
        return Result.ok();
    }
}
