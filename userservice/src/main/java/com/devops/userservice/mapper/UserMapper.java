package com.devops.userservice.mapper;

import com.devops.userservice.dto.UserDto;
import com.devops.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toUser(UserDto dto);
    List<UserDto> toDtos(List<User> users);
}
