package com.sang.common.domain.auth.authorization.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sang.common.validate.Delete;
import com.sang.common.validate.Update;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 用户模块
 * 用户扩展信息
 * 数据传输对象
 * hxy 2022-10-24 17:02:14
*/
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserExtDto {

    /**
     * id
    */
    @JsonSerialize(using = ToStringSerializer.class)
    @NotNull(message = "id不能为空",groups = {Delete.class,Update.class})
    private Long id;

}