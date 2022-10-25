package com.sang.common.domain.auth.authorization.user.mapper;

import com.sang.common.domain.auth.authorization.user.dto.UserDto;
import com.sang.common.domain.auth.authorization.user.dto.UserInfoDto;
import com.sang.common.domain.auth.authorization.user.entity.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(builder = @Builder(disableBuilder = true))
public interface UserMapper {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    List<UserDto> userToDtoList(List<User> list);

    List<User> dtoToUserList(List<UserDto> list);

    User dtoToUser(UserDto roleDto);

    UserDto userToDto(User roleDto);

    @Mapping(target = "avatar", source = "userExt.avatar")
    UserInfoDto userToInfoDto(User roleDto);

}
