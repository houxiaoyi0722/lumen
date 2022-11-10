package com.sang.common.domain.auth.authorization.user.mapper;

import com.sang.common.domain.auth.authorization.user.dto.UserDto;
import com.sang.common.domain.auth.authorization.user.dto.UserInfoDto;
import com.sang.common.domain.auth.authorization.user.entity.User;
import com.sang.common.domain.auth.authorization.user.vo.UserVo;
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

    User dtoToUser(UserDto userDto);

    UserDto userToDto(User user);

    @Mapping(target = "userGroup",source = "userGroup.id")
    UserVo userToVo(User user);

    @Mapping(target = "avatar", source = "userExt.avatar")
    UserInfoDto userToInfoDto(User userDto);

    List<UserVo> userToVoList(List<User> list);
}
