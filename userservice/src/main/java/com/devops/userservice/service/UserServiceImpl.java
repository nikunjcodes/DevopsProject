package com.devops.userservice.service;

import com.devops.userservice.dto.UserDto;
import com.devops.userservice.mapper.UserMapper;
import com.devops.userservice.model.User;
import com.devops.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id does not exist"));

        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.toUser(userDto);
        return UserMapper.INSTANCE.toDto(userRepository.save(user));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return UserMapper.INSTANCE.toDtos(userRepository.findAll());
    }
}
