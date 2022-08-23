package com.example.bloggingapplicationapi.services;

import com.example.bloggingapplicationapi.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Long userId);
    UserDto findUserById(Long id);
    List<UserDto> getAllUser();
    void deleteUser(Long userId);

}
