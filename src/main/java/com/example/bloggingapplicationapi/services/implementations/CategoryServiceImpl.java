package com.example.bloggingapplicationapi.services.implementations;

import com.example.bloggingapplicationapi.entities.Category;
import com.example.bloggingapplicationapi.exceptions.ResourceNotFound;
import com.example.bloggingapplicationapi.payloads.CategoryDto;
import com.example.bloggingapplicationapi.repositories.CategoryRepository;
import com.example.bloggingapplicationapi.services.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {

        CategoryDto oldCategory = this.getCategoryById(categoryId);
        if(categoryDto.getDescription() != null){
            oldCategory.setDescription(categoryDto.getDescription());
        }
        if(categoryDto.getTitle() != null){
            oldCategory.setTitle(categoryDto.getTitle());
        }
        Category category = modelMapper.map(oldCategory, Category.class);
        category = categoryRepository.save(category);
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto>  categoryDtos = new ArrayList<>();

        for(Category category : categories){
            categoryDtos.add(modelMapper.map(category, CategoryDto.class));
        }
        return categoryDtos;
    }

    @Override
    public void deleteCategoryById(Long categoryId) {
        CategoryDto categoryDto = this.getCategoryById(categoryId);
        if(categoryDto != null){
            categoryRepository.delete(modelMapper.map(categoryDto, Category.class));
        }
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFound("Category id : " + categoryId + " not found"));
        return modelMapper.map(category, CategoryDto.class);
    }
}
