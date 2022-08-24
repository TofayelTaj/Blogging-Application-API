package com.example.bloggingapplicationapi.repositories;

import com.example.bloggingapplicationapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostsByUserId(Long userId);
    List<Post> findPostsByCategoryId(Long categoryId);

}
