package com.sang.common.domain.auth.authentication.role.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hxy
 * @date 2022/1/5 16:20
 **/
@Builder @Getter @Setter @AllArgsConstructor @NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {

    /**
     * id 主键
     */
    @NotNull(message = "id不能为空",groups = {Delete.class,Update.class})
    private Long id;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空",groups = {Create.class,Update.class})
    @Length(max = 20,message = "角色名称长度在1-20之间",groups = {Create.class, Update.class})
    private String roleName;

    /**
     * 角色代码
     */
    @NotBlank(message = "角色代码不能为空",groups = {Create.class,Update.class})
    @Length(max = 20,message = "角色代码长度在1-20之间",groups = {Create.class, Update.class})
    private String roleCode;

    /**
     * 备注
     */
    @Length(max = 200,message = "备注长度不能超过200",groups = {Create.class, Update.class})
    private String comment;

    /**
     * @JsonIgnore
     * parentId
     */
    private RoleDto parentId;
}
