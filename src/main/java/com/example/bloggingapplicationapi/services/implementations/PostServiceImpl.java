package com.example.bloggingapplicationapi.services.implementations;

import com.example.bloggingapplicationapi.entities.Category;
import com.example.bloggingapplicationapi.entities.Post;
import com.example.bloggingapplicationapi.entities.User;
import com.example.bloggingapplicationapi.exceptions.ResourceNotFound;
import com.example.bloggingapplicationapi.payloads.PostDto;
import com.example.bloggingapplicationapi.repositories.PostRepository;
import com.example.bloggingapplicationapi.services.IPostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Post oldPost = postRepository.findById(postId).orElseThrow(()->new ResourceNotFound("Post Not Found!"));
        oldPost.setTitle(postDto.getTitle());
        oldPost.setContent(postDto.getContent());
        oldPost.setImageName(postDto.getImageName());
        Post post = postRepository.save(oldPost);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostByUserId(Long userId) {
        List<Post> posts = postRepository.findPostsByUserId(userId);
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        }
        return postDtos;
    }

    @Override
    public void deletePostByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFound("Post Not Found ! "));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategoryId(Long categoryId) {
        List<Post> posts = postRepository.findPostsByCategoryId(categoryId);
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        }
        return postDtos;
    }
}
