package com.sang.common.domain.auth.authentication.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sang.common.domain.base.dto.CommonIdDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVo {

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
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 是否在线
     */
    private Boolean online;

    /**
     * 账户未过期
     */
    private Boolean accountNonExpired;

    /**
     * 账户锁定
     */
    private Boolean accountNonLocked;

    /**
     * 凭证未过期
     */
    private Boolean credentialsNonExpired;

    /**
     * @JsonIgnore
     */
    private List<CommonIdDto> roles;

    /**
     * @JsonIgnore
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userGroup;

}
