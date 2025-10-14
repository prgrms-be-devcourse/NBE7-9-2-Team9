package com.backend.domain.category.controller;

import com.backend.domain.category.dto.CategoryDto;
import com.backend.domain.category.entity.Category;
import com.backend.domain.category.service.CategoryService;
import com.backend.global.reponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private  final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories(){
        List<CategoryDto> categories = categoryService.findAll();
        return ResponseEntity.ok(ApiResponse.success(categories));
    }

}
