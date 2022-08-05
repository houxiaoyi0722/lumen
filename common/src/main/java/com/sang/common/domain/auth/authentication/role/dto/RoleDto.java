package com.sang.common.domain.auth.authentication.role.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

/**
 * @author hxy
 * @date 2022/1/5 16:20
 **/
@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色代码
     */
    private String roleCode;

    /**
     * 备注
     */
    private String comment;

    /**
     * 子列表
     */
    private List<RoleDto> childList;
}
