package com.ecom.controllers;

import com.ecom.Model.Category;
import com.ecom.config.AppConstants;
import com.ecom.payload.CategoryDTO;
import com.ecom.payload.CategoryResponse;
import com.ecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
   private  CategoryService categoryService;

//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name="pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize){
        CategoryResponse categoryResponse=categoryService.getAllCategory(pageNumber,pageSize);
       return ResponseEntity.status(HttpStatus.OK).body(categoryResponse);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO returnedCategory=categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(returnedCategory);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId){

        CategoryDTO categoryDTO=categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(categoryDTO );
//
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId ){

            CategoryDTO savedCategory =categoryService.updateCategory(categoryDTO,categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(savedCategory);


    }
}
