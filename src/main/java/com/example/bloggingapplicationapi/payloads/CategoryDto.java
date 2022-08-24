package com.example.bloggingapplicationapi.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {

    private Long id;
    @NotBlank(message = "Title can't be empty.")
    private String title;
    @NotBlank
    @Size(max = 50, message = "Description size max 50 character.")
    private String description;
}
