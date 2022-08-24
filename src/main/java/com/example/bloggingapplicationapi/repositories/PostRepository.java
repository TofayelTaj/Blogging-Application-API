package com.example.bloggingapplicationapi.repositories;

import com.example.bloggingapplicationapi.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
