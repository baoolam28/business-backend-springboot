package com.onestep.business_management.Service.CategoryService;

import com.onestep.business_management.DTO.CategoryDTO.CategoryRequest;
import com.onestep.business_management.DTO.CategoryDTO.CategoryResponse;
import com.onestep.business_management.Entity.Category;
import com.onestep.business_management.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    // Create or Update Category
    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = CategoryMapper.INSTANCE.toEntity(categoryRequest);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toResponse(savedCategory);
    }

    // Get Category by ID
    public CategoryResponse getCategoryById(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(CategoryMapper.INSTANCE::toResponse).orElse(null);
    }

    // Get All Categories
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryMapper.INSTANCE::toResponse)
                .collect(Collectors.toList());
    }

    // Delete Category by ID
    public void deleteCategoryById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
