package com.example.bloggingapplicationapi.controllers;

import com.example.bloggingapplicationapi.entities.Post;
import com.example.bloggingapplicationapi.payloads.PageResponse;
import com.example.bloggingapplicationapi.payloads.PostDto;
import com.example.bloggingapplicationapi.services.implementations.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostServiceImpl postService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}")
    public ResponseEntity<PostDto> createpost(
            @RequestBody PostDto postDto,
            @PathVariable Long userId,
            @PathVariable Long categoryId
            ){
        postDto = postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<PageResponse<PostDto>> getPostsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "10", required = false) int size,
            @RequestParam(defaultValue = "asc", required = false) String sortDir,
            @RequestParam(defaultValue = "id", required = false) String sortBy
            ){
        PageResponse<PostDto> pageResponse = postService.getPostByUserId(userId, page, size, sortBy, sortDir);
        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
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

    @GetMapping("/search/{searchText}")
    public ResponseEntity<List<PostDto>> searchByTitle(@PathVariable String searchText){
        List<PostDto> postDtos = postService.searchByTitle(searchText);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
    @PostMapping(value = "/upload/user/{userId}/post/{postId}")
    public ResponseEntity uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Long userId,
            @PathVariable Long postId
    ) throws IOException {

        PostDto postDto = postService.getSinglePostByUserIdAndPostId(userId, postId).getBody();
        postService.uploadImage(path, image);
        postDto.setImageName(image.getOriginalFilename());
        postService.updatePost(postDto, postDto.getCategoryId());
        return new ResponseEntity(HttpStatus.OK);

    }


    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(@PathVariable String imageName,
                           HttpServletResponse response
                           ) throws IOException {

        InputStream inputStream = postService.serveImage(path, imageName);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }

}
