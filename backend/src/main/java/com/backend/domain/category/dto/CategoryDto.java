package com.backend.domain.category.dto;

import com.backend.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;

    public static CategoryDto from(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}