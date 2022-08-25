package com.example.bloggingapplicationapi.repositories;

import com.example.bloggingapplicationapi.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByUserId(Long userId, Pageable pageable);
    List<Post> findPostsByCategoryId(Long categoryId);

    List<Post> findPostsByTitleContaining(String searchText);

}
