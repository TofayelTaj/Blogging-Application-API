package com.example.bloggingapplicationapi.services.implementations;

import com.example.bloggingapplicationapi.entities.Comment;
import com.example.bloggingapplicationapi.entities.Post;
import com.example.bloggingapplicationapi.exceptions.ResourceNotFound;
import com.example.bloggingapplicationapi.payloads.CommentDto;
import com.example.bloggingapplicationapi.repositories.CommentRepository;
import com.example.bloggingapplicationapi.repositories.PostRepository;
import com.example.bloggingapplicationapi.services.ICommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {
        Comment  comment = modelMapper.map(commentDto, Comment.class);
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFound("Post Not Found"));
        comment.setPost(post);
        commentDto = modelMapper.map(commentRepository.save(comment), CommentDto.class);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Long commentId) {
        Comment oldComment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFound("comment not found"));
        oldComment.setComment(commentDto.getComment());
        oldComment = commentRepository.save(oldComment);
        return modelMapper.map(oldComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFound("comment not found"));
        commentRepository.delete(comment);
    }

    @Override
    public List<CommentDto> getAllCommentByPostId(Long postId) {
        List<CommentDto> commentDtos = new ArrayList<>();
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        for(Comment comment : comments){
            commentDtos.add(modelMapper.map(comment, CommentDto.class));
        }
        return commentDtos;
    }
}
