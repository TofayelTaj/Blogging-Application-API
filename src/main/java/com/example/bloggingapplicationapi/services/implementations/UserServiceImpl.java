package com.example.bloggingapplicationapi.services.implementations;

import com.example.bloggingapplicationapi.entities.User;
import com.example.bloggingapplicationapi.exceptions.ResourceNotFound;
import com.example.bloggingapplicationapi.payloads.UserDto;
import com.example.bloggingapplicationapi.repositories.UserRepository;
import com.example.bloggingapplicationapi.services.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User savedUser = userRepository.save(this.dtoToUser(userDto));
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {

        UserDto oldUser = this.findUserById(userId);
        if (userDto.getName() != null) {
            oldUser.setName(userDto.getName());
        }
        if (userDto.getPassword() != null) {
            oldUser.setPassword(userDto.getPassword());
        }
        if (userDto.getEmail() != null) {
            oldUser.setEmail(userDto.getEmail());
        }
        if (userDto.getAbout() != null) {
            oldUser.setAbout(userDto.getAbout());
        }
        return this.userToDto(userRepository.save(this.dtoToUser(oldUser)));
    }

    @Override
    public UserDto findUserById(Long userId) {
        User user;
        user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound(String.format("Id : %s Not Found !", userId)));
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
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public User findUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }

}
