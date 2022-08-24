package com.example.bloggingapplicationapi.repositories;

import com.example.bloggingapplicationapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
