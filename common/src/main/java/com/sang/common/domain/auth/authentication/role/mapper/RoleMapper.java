package com.sang.common.domain.auth.authentication.role.mapper;

import com.sang.common.domain.auth.authentication.role.dto.RoleDto;
import com.sang.common.domain.auth.authentication.role.entity.Role;
import com.sang.common.domain.auth.authentication.role.vo.RoleVo;
import com.sang.common.domain.base.dto.CommonKeyValueDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(builder = @Builder(disableBuilder = true))
public interface RoleMapper {

    RoleMapper mapper = Mappers.getMapper( RoleMapper.class );

    List<RoleDto> roleToDtoList(List<Role> list);

    List<RoleVo> roleToVoList(List<Role> list);

    @Mapping(target = "parentId",source = "parentId.id")
    RoleVo roleToVo(Role role);

    List<Role> dtoToRoleList(List<RoleDto> list);

    Role dtoToRole(RoleDto roleDto);

    @Mapping(target = "label",source = "role.roleName")
    @Mapping(target = "value",source = "role.id")
    CommonKeyValueDto toKeyValue(Role role);

    List<CommonKeyValueDto> toKeyValueList(List<Role> list);


}
