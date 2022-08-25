package com.example.bloggingapplicationapi.services;

import com.example.bloggingapplicationapi.payloads.PageResponse;
import com.example.bloggingapplicationapi.payloads.PostDto;

import java.util.List;

public interface IPostService {

    PostDto createPost(PostDto postDto, Long userId, Long CategoryId);
    PostDto updatePost(PostDto postDto, Long postId);
    PageResponse<PostDto> getPostByUserId(Long userId, int page, int size);
    void deletePostByPostId(Long postId);
    List<PostDto> getPostsByCategoryId(Long categoryId);


}
