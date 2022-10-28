package com.sang.common.domain.auth.authentication.role.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleTableDataDto {
    private List<RoleDto> insertList;
    private List<RoleDto> updateList;
    private List<RoleDto> removeList;
}
