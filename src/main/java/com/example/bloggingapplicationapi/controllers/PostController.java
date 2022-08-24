package com.example.bloggingapplicationapi.controllers;

import com.example.bloggingapplicationapi.payloads.PostDto;
import com.example.bloggingapplicationapi.services.implementations.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId){
        List<PostDto> postDtos = postService.getPostByUserId(userId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable Long categoryId){
        List<PostDto> postDtos = postService.getPostsByCategoryId(categoryId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Long postId){
        postDto = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(@PathVariable Long postId){
        postService.deletePostByPostId(postId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}