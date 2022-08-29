package com.example.bloggingapplicationapi.security;

import com.example.bloggingapplicationapi.entities.User;
import com.example.bloggingapplicationapi.services.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("user not found !");
        }
        AppUserDetails appUserDetails = new AppUserDetails(user);
        return appUserDetails;
    }
}
