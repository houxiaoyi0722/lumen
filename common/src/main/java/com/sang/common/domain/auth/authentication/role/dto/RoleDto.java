package com.sang.common.domain.auth.authentication.role.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sang.common.domain.base.dto.CommonIdDto;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @JsonSerialize(using = ToStringSerializer.class)
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

    /**
     * 权限id列表
     */
    private List<CommonIdDto> permissions;
}
