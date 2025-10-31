package com.ecom.service;

import com.ecom.Model.Category;
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
    private Long nextId=1L;
    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        List<Category> categories=categoryRepository.findAll();
        Category category =categories.stream()
                .filter(c->c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));

//        if(category==null){
//            return "No category with ID: "+categoryId+" found";
//        }
        categoryRepository.delete(category);
        return "Category with categoryId: "+categoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category,Long categoryID) {
        List<Category> categories=categoryRepository.findAll();
        Optional<Category>category1=categories.stream().filter(c->c.getCategoryId().equals(categoryID))
                .findFirst();

        if(category1.isPresent()){
            Category existingCategory=category1.get();
            existingCategory.setCategoryName(category.getCategoryName());
            existingCategory.setCategoryId(category.getCategoryId());
            categoryRepository.save(existingCategory);
            return existingCategory;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found");
        }


    }

}
