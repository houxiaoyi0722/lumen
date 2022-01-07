package com.sang.system.controller.role;

import com.sang.common.domain.role.entity.Role;
import com.sang.common.response.Result;
import com.sang.system.service.role.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hxy
 * @date 2021/12/31 17:57
 **/
@RestController
@RequestMapping("/lumen/role")
public class RoleController {

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
    public Result<List<Role>> roles(@RequestParam Long parentId) {
        return Result.ok(roleService.rolesByParentId(parentId));
    }

    @PostMapping("/role")
    public Result<Boolean> saveRole(@RequestBody Role role) {
        roleService.save(role);
        return Result.ok();
    }

    @PutMapping("/role")
    public Result<Boolean> updateRole(@RequestBody Role role) {
        roleService.update(role);
        return Result.ok();
    }

    @PostMapping("/roles")
    public Result<Boolean> saveAll(@RequestBody List<Role> roles) {
        roleService.saveAll(roles);
        return Result.ok();
    }

    @DeleteMapping("/role")
    public Result<Boolean> delete(@RequestBody Role role) {
        roleService.delete(role);
        return Result.ok();
    }

    @DeleteMapping("/roles")
    public Result<Boolean> deleteAll(@RequestBody List<Role> roles) {
        roleService.deleteAll(roles);
        return Result.ok();
    }



}
