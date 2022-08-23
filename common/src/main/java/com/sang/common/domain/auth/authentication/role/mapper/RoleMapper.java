package com.sang.common.domain.auth.authentication.role.mapper;

import com.sang.common.domain.auth.authentication.role.dto.RoleDto;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface RoleMapper {

    RoleMapper mapper = Mappers.getMapper( RoleMapper.class );

    List<RoleDto> roleToDtoList(List<Role> list);

    List<Role> dtoToRoleList(List<RoleDto> list);

    Role dtoToRole(RoleDto roleDto);

}
