package com.example.bloggingapplicationapi.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private long id;
    private String name;
    private String email;
    private  String password;
    private String about;

}
