package com.example.bloggingapplicationapi.services.implementations;

import com.example.bloggingapplicationapi.entities.Category;
import com.example.bloggingapplicationapi.entities.Post;
import com.example.bloggingapplicationapi.entities.User;
import com.example.bloggingapplicationapi.payloads.PostDto;
import com.example.bloggingapplicationapi.repositories.PostRepository;
import com.example.bloggingapplicationapi.services.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PostServiceImpl implements IPostService {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private CategoryServiceImpl categoryService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
        Post post = new Post();
        User user = modelMapper.map( userService.findUserById(userId), User.class);
        Category category = modelMapper.map(categoryService.getCategoryById(categoryId), Category.class);
        post.setCategory(category);
        post.setUser(user);
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        post = postRepository.save(post);
        PostDto createdPost = modelMapper.map(post, PostDto.class);
        return createdPost;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        return null;
    }

    @Override
    public List<PostDto> getPostByUserId(Long userId) {
        return null;
    }

    @Override
    public void deletePostByPostId(Long postId) {

    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {
        return null;
    }
}
