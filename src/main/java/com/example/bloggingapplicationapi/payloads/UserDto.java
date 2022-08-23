package com.example.bloggingapplicationapi.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private long id;
    @NotBlank(message = "Name can't be empty.")
    @Size(min = 3, max = 20, message = "name must be between 3 to 20 character.")
    private String name;
    @NotBlank(message = "Email can't be empty.")
    @Email(message = "Not a valid Email.")
    private String email;
    @NotBlank(message = "Password can't be empty.")
    @Size(min = 8, message = "Password must be at least 8 character.")
    private  String password;
    private String about;

}
