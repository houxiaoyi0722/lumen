package com.sang.system.controller.permissions;

import com.sang.common.response.PageResult;
import com.sang.common.response.Result;
import com.sang.common.domain.auth.authentication.permissions.mapper.PermissionsMapper;
import com.sang.common.domain.auth.authentication.permissions.entity.Permissions;
import com.sang.common.domain.auth.authentication.permissions.param.PermissionsQry;
import com.sang.system.service.permissions.PermissionsService;
import com.sang.common.domain.auth.authentication.permissions.dto.PermissionsDto;
import org.springframework.web.bind.annotation.*;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import org.springframework.validation.annotation.Validated;
import io.ebean.PagedList;

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

    private final PermissionsMapper permissionsMapper = PermissionsMapper.mapper;

    @Resource
    private PermissionsService permissionsService;

    /**
     * 分页查询
     *
     * @param qry
     * @return
     */
    @PostMapping("/permission/qry")
    public PageResult<PermissionsDto> list(@RequestBody PermissionsQry qry) {
        PagedList<Permissions> pagedList = permissionsService.permissionsList(qry);
        // 查询全部字段时可不转换直接给pagedList
        return PageResult.ok(permissionsMapper.permissionsToDtoList(pagedList.getList()), pagedList);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("/permissions")
    public Result<Permissions> findOne(@RequestParam("id") Long id) {
        return Result.ok(permissionsService.findOne(id));
    }

    /**
     * 保存
     *
     * @param permissions
     * @return
     */
    @PostMapping("/permissions")
    public Result<Boolean> save(@RequestBody @Validated(Create.class) PermissionsDto permissions) {
        permissionsService.save(permissionsMapper.dtoToPermissions(permissions));
        return Result.ok();
    }

    /**
    * 批量保存
    * @param permissions
    * @return
    */
    @PostMapping("/permission")
    public Result<Boolean> saveAll(@RequestBody @Validated(Create.class) List<PermissionsDto> permissions) {
        permissionsService.saveAll(permissionsMapper.dtoToPermissionsList(permissions));
        return Result.ok();
    }

    /**
     * 通过id更新
     *
     * @param permissions
     * @return
     */
    @PutMapping("/permissions")
    public Result<Boolean> update(@RequestBody @Validated(Update.class) PermissionsDto permissions) {
        permissionsService.update(permissionsMapper.dtoToPermissions(permissions));
        return Result.ok();
    }

    /**
     * 通过id删除
     *
     * @param permissions
     * @return
     */
    @DeleteMapping("/permissions")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) PermissionsDto permissions) {
        permissionsService.delete(permissionsMapper.dtoToPermissions(permissions));
        return Result.ok();
    }

    /**
     * 列表批量删除
     *
     * @param permissions
     * @return
     */
    @DeleteMapping("/permission")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) List<PermissionsDto> permissions) {
        permissionsService.deleteAll(permissionsMapper.dtoToPermissionsList(permissions));
        return Result.ok();
    }
}