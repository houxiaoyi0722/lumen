package com.sang.common.domain.auth.authentication.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sang.common.domain.auth.authorization.role.dto.RoleDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDto {

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 姓名
     */
    private String name;

    /**
     * 用户名
     */
    private String username;

    /**
     * 电话
     */
    private String phone;

    /**
     * 移动电话
     */
    private String mobilePhone;

    /**
     * 地址
     */
    private String address;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * @JsonIgnore
     */
    private List<RoleDto> roles;

    /**
     * @JsonIgnore
     */
    private UserGroupDto userGroup;

    /**
     * 用户头像
     */
    private UserExtDto userExt;

}
