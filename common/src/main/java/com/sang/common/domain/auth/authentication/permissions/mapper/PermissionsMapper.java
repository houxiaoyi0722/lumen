package com.sang.common.domain.auth.authentication.permissions.mapper;

import com.sang.common.domain.auth.authentication.permissions.dto.PermissionsDto;
import com.sang.common.domain.auth.authentication.permissions.entity.Permissions;
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
public interface PermissionsMapper {

    PermissionsMapper mapper = Mappers.getMapper( PermissionsMapper.class );

    List<PermissionsDto> permissionsToDtoList(List<Permissions> list);

    List<Permissions> dtoToPermissionsList(List<PermissionsDto> list);

    Permissions dtoToPermissions(PermissionsDto permissionsDto);

    PermissionsDto permissionsToDto(Permissions permissions);

}