package com.example.bloggingapplicationapi.configurations;

import com.example.bloggingapplicationapi.payloads.CommentDto;
import com.example.bloggingapplicationapi.services.implementations.CommentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Long postId){
       commentDto = commentService.createComment(commentDto, postId);
       return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto, @PathVariable Long commentId){
        commentDto = commentService.updateComment(commentDto, commentId);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentByPostId(@PathVariable Long postId){
        List<CommentDto> commentDtos = commentService.getAllCommentByPostId(postId);
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

}
