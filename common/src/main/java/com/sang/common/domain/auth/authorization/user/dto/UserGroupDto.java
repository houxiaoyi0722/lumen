package com.sang.common.domain.auth.authorization.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGroupDto {

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

    private UserGroupDto parentId;
}
