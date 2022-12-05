package com.sang.common.domain.auth.authorization.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sang.common.constants.RegexConst;
import com.sang.common.domain.auth.authentication.role.dto.RoleDto;
import com.sang.common.validate.Create;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import com.sang.common.validate.user.ResetPassword;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {Delete.class, Update.class, ResetPassword.class})
    private Long id;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空",groups = {Create.class, Update.class})
    @Length(max = 100,message = "姓名长度在1-100之间",groups = {Create.class, Update.class})
    private String name;

    /**
     * 用户名
     */
    @NotBlank(message = "账号不能为空",groups = {Create.class, Update.class})
    @Length(max = 100,message = "账号长度在1-100之间",groups = {Create.class, Update.class,ResetPassword.class})
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空",groups = {Create.class})
    @Length(max = 100,message = "密码长度在1-100之间",groups = {Create.class, ResetPassword.class})
    private String password;

    /**
     * 电话
     */
    @Length(max = 20,message = "密码长度在20之内",groups = {Create.class, Update.class})
    private String phone;

    /**
     * 移动电话
     */
    @Length(max = 11,min = 11,message = "长度应该为11",groups = {Create.class, Update.class})
    @Pattern(regexp = RegexConst.MOBILE_PHONE,message = "手机号格式不正确", groups = {Create.class, Update.class})
    private String mobilePhone;

    /**
     * 地址
     */
    @Length(max = 200,message = "地址长度应小于200",groups = {Create.class, Update.class})
    private String address;

    /**
     * 邮箱地址
     */
    @Email(message = "邮箱格式不正确",groups = {Create.class, Update.class})
    private String email;

    /**
     * 是否启用
     */
    @NotNull(message = "是否启用不能为空",groups = {Create.class, Update.class})
    private Boolean enabled;

    /**
     * 账户未过期
     */
    @NotNull(message = "账户未过期不能为空",groups = {Create.class, Update.class})
    private Boolean accountNonExpired;

    /**
     * 账户锁定
     */
    @NotNull(message = "账户锁定不能为空",groups = {Create.class, Update.class})
    private Boolean accountNonLocked;

    /**
     * 凭证未过期
     */
    @NotNull(message = "凭证未过期不能为空",groups = {Create.class, Update.class})
    private Boolean credentialsNonExpired;

    /**
     * @JsonIgnore
     */
    private List<RoleDto> roles;

    /**
     * @JsonIgnore
     */
    private UserGroupDto userGroup;

}
