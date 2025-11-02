package com.ecom.service;

import com.ecom.Model.Category;
import com.ecom.exception.ApiException;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;
//    /private List<Category>categories =new ArrayList<>();
//    private Long nextId=1L;
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
//        category.setCategoryId(nextId++);
       Category savedCat= categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCat!=null){
            throw new ApiException("category with name "+category.getCategoryName()+" already exist");
        }
       categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category savedCategory =categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        categoryRepository.delete(savedCategory);
        return "Category with categoryId: "+categoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category,Long categoryID) {
        Optional<Category> savedCategoryOptional=categoryRepository.findById(categoryID);

        Category savedCategory= savedCategoryOptional
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryID));
        category.setCategoryId(categoryID);

        categoryRepository.save(category);


        return savedCategory;


    }

}
