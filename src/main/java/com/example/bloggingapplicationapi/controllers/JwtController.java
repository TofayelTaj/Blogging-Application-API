package com.example.bloggingapplicationapi.controllers;

import com.example.bloggingapplicationapi.Utility.JwtUtil;
import com.example.bloggingapplicationapi.entities.JwtRequest;
import com.example.bloggingapplicationapi.entities.JwtResponse;
import com.example.bloggingapplicationapi.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest jwtRequest) throws Exception {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword()));
        }catch (BadCredentialsException exception){
            throw  new Exception("Invalid user name or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUserName());
        final String token = jwtUtil.generateToken(userDetails);
        return new JwtResponse(token);
    }

}
