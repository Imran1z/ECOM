package com.ecom.service;

import com.ecom.Model.Category;
import com.ecom.payload.CategoryDTO;
import com.ecom.payload.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
