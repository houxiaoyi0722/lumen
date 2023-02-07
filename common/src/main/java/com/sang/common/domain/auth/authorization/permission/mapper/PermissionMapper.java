package com.sang.common.domain.auth.authorization.permission.mapper;

import com.sang.common.domain.auth.authorization.permission.dto.PermissionDto;
import com.sang.common.domain.auth.authorization.permission.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.Builder;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 * 权限管理
 * 操作权限
 * 模型映射类
 * hxy 2022-12-13 14:11:40
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface PermissionMapper {

    PermissionMapper mapper = Mappers.getMapper( PermissionMapper.class );

    List<PermissionDto> permissionsToDtoList(List<Permission> list);

    List<Permission> dtoToPermissionsList(List<PermissionDto> list);

    Permission dtoToPermissions(PermissionDto permissionDto);

    PermissionDto permissionsToDto(Permission permission);

}
