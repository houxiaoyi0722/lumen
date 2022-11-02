package com.sang.common.domain.auth.authorization.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserGroupTableDataDto {
    private List<UserGroupDto> insertList;
    private List<UserGroupDto> updateList;
    private List<UserGroupDto> removeList;
}
