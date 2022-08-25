package com.example.bloggingapplicationapi.services;

import com.example.bloggingapplicationapi.payloads.CommentDto;

import java.util.List;

public interface ICommentService {

    CommentDto createComment(CommentDto commetDto, Long postId);

    CommentDto updateComment(CommentDto commetDto, Long commentId);

    void deleteComment(Long commentId);

    List<CommentDto> getAllCommentByPostId(Long postId);


}
