package com.sang.common.domain.auth.authorization.user.mapper;

import com.sang.common.domain.auth.authorization.user.dto.UserGroupDto;
import com.sang.common.domain.auth.authorization.user.entity.UserGroup;
import com.sang.common.domain.auth.authorization.user.vo.UserGroupVo;
import com.sang.common.domain.base.dto.CommonKeyValueDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 * 用户模块
 * 用户组
 * 模型映射类
 * hxy 2022-11-02 17:04:18
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface UserGroupMapper {

    UserGroupMapper mapper = Mappers.getMapper( UserGroupMapper.class );

    List<UserGroupDto> userGroupToDtoList(List<UserGroup> list);

    List<UserGroupVo> userGroupToVoList(List<UserGroup> list);

    @Mapping(source = "parentId.id",target = "parentId")
    UserGroupVo userGroupToVo(UserGroup userGroup);

    List<UserGroup> dtoToUserGroupList(List<UserGroupDto> list);

    UserGroup dtoToUserGroup(UserGroupDto userGroupDto);

    UserGroupDto userGroupToDto(UserGroup userGroup);

    @Mapping(target = "label",source = "userGroup.groupName")
    @Mapping(target = "value",source = "userGroup.id")
    CommonKeyValueDto toKeyValue(UserGroup userGroup);

    List<CommonKeyValueDto> toKeyValueList(List<UserGroup> list);

}