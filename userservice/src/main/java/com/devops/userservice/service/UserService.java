package com.devops.userservice.service;

import com.devops.userservice.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    UserDto createUser(UserDto userDto);
    List<UserDto> getAllUsers();
}
