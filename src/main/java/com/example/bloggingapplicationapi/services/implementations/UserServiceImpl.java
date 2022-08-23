package com.example.bloggingapplicationapi.services.implementations;

import com.example.bloggingapplicationapi.entities.User;
import com.example.bloggingapplicationapi.exceptions.ResourceNotFound;
import com.example.bloggingapplicationapi.payloads.UserDto;
import com.example.bloggingapplicationapi.repositories.UserRepository;
import com.example.bloggingapplicationapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(this.dtoToUser(userDto));
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        UserDto oldUser = this.findUserById(userId);
        oldUser.setAbout(userDto.getAbout());
        oldUser.setName(userDto.getName());
        oldUser.setEmail(userDto.getEmail());
        oldUser.setPassword(userDto.getPassword());
        return this.userToDto(userRepository.save(this.dtoToUser(oldUser)));
    }

    @Override
    public UserDto findUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        if (user == null) {
            throw new ResourceNotFound(String.format("%l Id Not Found !", userId));
        }
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(this.userToDto(user));
        }
        return userDtoList;
    }

    @Override
    public void deleteUser(Long userId) {
        UserDto userDto = findUserById(userId);
        userRepository.delete(this.dtoToUser(userDto));
    }


    public User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setAbout(user.getAbout());
        return userDto;
    }

}
