package com.example.bloggingapplicationapi.controllers;

import com.example.bloggingapplicationapi.payloads.CategoryDto;
import com.example.bloggingapplicationapi.services.implementations.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    public CategoryServiceImpl categoryService;
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        categoryDto = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long categoryId){
       CategoryDto categoryDto = categoryService.getCategoryById(categoryId);
       return new ResponseEntity<>(categoryDto, HttpStatus.FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        List<CategoryDto> categoryDtos = categoryService.getAllCategory();
        return new ResponseEntity<>(categoryDtos, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity deleteCategoryById(@PathVariable Long categoryId){
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId){
        categoryDto = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }




}
