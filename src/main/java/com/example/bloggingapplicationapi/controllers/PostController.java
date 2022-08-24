package com.example.bloggingapplicationapi.controllers;

import com.example.bloggingapplicationapi.payloads.PostDto;
import com.example.bloggingapplicationapi.services.implementations.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostServiceImpl postService;

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createpost(
            @RequestBody PostDto postDto,
            @PathVariable Long userId,
            @PathVariable Long categoryId){
        postDto = postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }
}
