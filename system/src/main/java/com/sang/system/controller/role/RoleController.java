package com.sang.system.controller.role;

import com.sang.common.domain.role.entity.Role;
import com.sang.common.response.Result;
import com.sang.system.domain.role.dto.RoleDto;
import com.sang.system.service.role.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
