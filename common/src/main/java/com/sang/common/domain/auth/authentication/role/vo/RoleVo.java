package com.sang.common.domain.auth.authentication.role.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

import java.util.List;

/**
 * @author hxy
 * @date 2022/1/5 16:20
 **/
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVo {

    /**
     * id 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色代码
     */
    private String roleCode;

    /**
     * 描述
     */
    private String comment;

    /**
     * parentId
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
}
