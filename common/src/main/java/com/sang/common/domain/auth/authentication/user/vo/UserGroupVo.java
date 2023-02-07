package com.sang.common.domain.auth.authentication.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGroupVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 用户组名称
     */
    private String groupName;

    /**
     * 用户组代码
     */
    private String groupCode;

    /**
     * 备注
     */
    private String comment;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
}
