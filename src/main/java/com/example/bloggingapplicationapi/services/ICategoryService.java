package com.example.bloggingapplicationapi.services;

import com.example.bloggingapplicationapi.payloads.CategoryDto;

import java.util.List;

public interface ICategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);
    List<CategoryDto> getAllCategory();
    void deleteCategoryById(Long categoryId);
    CategoryDto getCategoryById(Long categoryId);
}
