package com.sang.common.domain.auth.authentication.permissions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.domain.router.entity.Router;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import io.ebean.annotation.DbComment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 权限管理
 * 操作权限
 * 数据传输对象
 * hxy 2022-12-13 14:11:40
*/
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionsDto {

    /**
     * id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {Delete.class,Update.class})
    private Long id;

    /**
     * 权限code
     */
    private String code;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 备注
     */
    private String comment;

    private List<Role> roles;

    private Router router;

}
