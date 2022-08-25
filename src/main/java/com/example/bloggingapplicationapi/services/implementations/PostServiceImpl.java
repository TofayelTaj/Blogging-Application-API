package com.example.bloggingapplicationapi.services.implementations;

import com.example.bloggingapplicationapi.entities.Category;
import com.example.bloggingapplicationapi.entities.Post;
import com.example.bloggingapplicationapi.entities.User;
import com.example.bloggingapplicationapi.exceptions.ResourceNotFound;
import com.example.bloggingapplicationapi.payloads.PageResponse;
import com.example.bloggingapplicationapi.payloads.PostDto;
import com.example.bloggingapplicationapi.repositories.PostRepository;
import com.example.bloggingapplicationapi.services.IPostService;
import com.example.bloggingapplicationapi.services.ISearchService;
import com.example.bloggingapplicationapi.services.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
public class PostServiceImpl implements IPostService, ISearchService<PostDto>, ImageService {

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
    public PageResponse<PostDto> getPostByUserId(Long userId, int page, int size,String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable p = PageRequest.of(page, size, sort);
        PageResponse<PostDto> pageResponse = new PageResponse<>();
        Page<Post> pagePosts = postRepository.findPostsByUserId(userId, p);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        }
        pageResponse.setContent(postDtos);
        pageResponse.setPageNumber(pagePosts.getNumber());
        pageResponse.setTotalPage(pagePosts.getTotalPages());
        pageResponse.setTotalElements(pagePosts.getTotalElements());
        pageResponse.setLastPage(pagePosts.isLast());
        return pageResponse;
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

    @Override
    public List<PostDto> searchByTitle(String searchText) {
        List<Post>  posts = postRepository.findPostsByTitleContaining(searchText);
        List<PostDto> postDtos = new ArrayList<>();
        for(Post post : posts){
            postDtos.add(modelMapper.map(post, PostDto.class));
        }
        return postDtos;
    }


    public ResponseEntity<PostDto> getSinglePostByUserIdAndPostId(Long userId, Long postId){
        Post post = postRepository.findPostByUserIdAndPostId(userId, postId);
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }


    @Override
    public void uploadImage(String path, MultipartFile image) throws IOException, FileAlreadyExistsException {
        String fileName = image.getOriginalFilename();
        String filePath = path + File.separator + fileName;
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        Files.copy(image.getInputStream(), Paths.get(filePath));
    }

    @Override
    public InputStream serveImage(String path, String imageName) throws FileNotFoundException {
        String fullPath = path + File.separator + imageName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }
}
