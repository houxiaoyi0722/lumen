package com.sang.common.domain.auth.authentication.user.mapper;

import com.sang.common.domain.auth.authentication.user.dto.UserExtDto;
import com.sang.common.domain.auth.authentication.user.entity.UserExt;
import org.mapstruct.Mapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * MapStruct转换器
 * 用户模块
 * 用户扩展信息
 * 模型映射类
 * hxy 2022-10-24 17:02:14
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface UserExtMapper {

    UserExtMapper mapper = Mappers.getMapper( UserExtMapper.class );

    List<UserExtDto> userExtToDtoList(List<UserExt> list);

    List<UserExt> dtoToUserExtList(List<UserExtDto> list);

    UserExt dtoToUserExt(UserExtDto userextDto);

    @Mapping(source = "userext.gender.code",target = "gender")
    @Mapping(source = "user", target = "user",ignore = true)
    UserExtDto userExtToDto(UserExt userext);

}
