package com.sang.system.controller.role;

import cn.hutool.core.collection.CollUtil;
import com.sang.common.domain.auth.authentication.role.dto.RoleDto;
import com.sang.common.domain.auth.authentication.role.dto.RoleTableDataDto;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.domain.auth.authentication.role.mapper.RoleMapper;
import com.sang.common.domain.auth.authentication.role.vo.RoleVo;
import com.sang.common.domain.base.dto.CommonKeyValueDto;
import com.sang.common.response.Result;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.system.service.role.RoleService;
import io.ebean.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色管理
 * @author hxy
 * @date 2021/12/31 17:57
 **/
@Validated
@RestController
@RequestMapping("/lumen/role")
public class RoleController {

    private final RoleMapper roleMapper = RoleMapper.mapper;

    @Resource
    private RoleService roleService;

    /**
     * 查询顶级角色
     * @return
     */
    @GetMapping("/topRoles")
    public Result<List<Role>> topRoles() {
        return Result.ok(roleService.findTopRoles());
    }

    /**
     * 根据父角色id获取子角色列表，为null获取顶级角色
     * @param parentId
     * @return
     */
    @GetMapping("/childRoles")
    public Result<List<Role>> roles(@RequestParam @NotNull(message = "parentId不能为空") Long parentId) {
        return Result.ok(roleService.rolesByParentId(parentId));
    }

    /**
     * 获取键值对角色树
     * @return
     */
    @GetMapping("/rolesKVTree")
    public Result<List<CommonKeyValueDto>> rolesKVTree() {
        return Result.ok(roleMapper.toKeyValueList(roleService.findTopRoles()));
    }

    /**
     * 查询角色树
     * @return
     */
    @GetMapping("/rolesTree")
    public Result<List<RoleVo>> rolesTree() {
        return Result.ok(roleMapper.roleToVoList(roleService.findAll()));
    }


    /**
     * 保存对象
     * @param role
     * @return
     */
    @PostMapping("/role")
    public Result<Boolean> saveRole(@RequestBody @Validated(Create.class) RoleDto role) {
        roleService.save(roleMapper.dtoToRole(role));
        return Result.ok();
    }

    /**
     * 按照id更新
     * @param role
     * @return
     */
    @PutMapping("/role")
    public Result<Boolean> updateRole(@RequestBody @Validated(Update.class) RoleDto role) {
        roleService.update(roleMapper.dtoToRole(role));
        return Result.ok();
    }

    /**
     * 批量保存
     * @param roles
     * @return
     */
    @PostMapping("/roles")
    public Result<Boolean> saveAll(@RequestBody @Validated(Create.class) List<RoleDto> roles) {
        roleService.saveAll(roleMapper.dtoToRoleList(roles));
        return Result.ok();
    }

    /**
     * 按id删除
     * @param role
     * @return
     */
    @DeleteMapping("/role")
    public Result<Boolean> delete(@RequestBody @Validated(Delete.class) RoleDto role) {
        roleService.delete(roleMapper.dtoToRole(role));
        return Result.ok();
    }

    /**
     * 批量删除
     * @param roles
     * @return
     */
    @DeleteMapping("/roles")
    public Result<Boolean> deleteAll(@RequestBody @Validated(Delete.class) List<RoleDto> roles) {
        roleService.deleteAll(roleMapper.dtoToRoleList(roles));
        return Result.ok();
    }

    /**
     * 批量保存/更新/删除数据 配合 vxe table
     * @param tableDataDto
     * @return
     */
    @Transactional
    @PostMapping("/roleListUpdate")
    public Result<Boolean> roleListUpdate(@RequestBody @Validated RoleTableDataDto tableDataDto) {
        if (CollUtil.isNotEmpty(tableDataDto.getInsertList())) {
            roleService.saveAll(roleMapper.dtoToRoleList(tableDataDto.getInsertList()));
        }
        if (CollUtil.isNotEmpty(tableDataDto.getUpdateList())) {
            roleService.updateAll(roleMapper.dtoToRoleList(tableDataDto.getUpdateList()));
        }
        if (CollUtil.isNotEmpty(tableDataDto.getRemoveList())) {
            roleService.deleteAll(roleMapper.dtoToRoleList(tableDataDto.getRemoveList()));
        }

        return Result.ok();
    }



}
