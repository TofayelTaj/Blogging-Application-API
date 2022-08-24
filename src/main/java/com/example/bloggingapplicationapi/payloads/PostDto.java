package com.example.bloggingapplicationapi.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private String imageName;


}
