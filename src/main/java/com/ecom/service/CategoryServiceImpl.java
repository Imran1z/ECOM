package com.ecom.service;

import com.ecom.Model.Category;
import com.ecom.exception.ApiException;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.payload.CategoryDTO;
import com.ecom.payload.CategoryResponse;
import com.ecom.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private ModelMapper mapper;
//    /private List<Category>categories =new ArrayList<>();
//    private Long nextId=1L;
    @Override
    public CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize) {

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize);
        Page<Category> categoryPage=categoryRepository.findAll(pageDetails);

        List<Category> categories =categoryPage.getContent();
        if (categories.isEmpty()){
            throw new ApiException("No categories to show");
        }
        List<CategoryDTO> categoryDTO=categories.stream()
                .map(category -> mapper.map(category,CategoryDTO.class)).toList();

        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTO);
        return categoryResponse ;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
//        category.setCategoryId(nextId++);
        Category category=mapper.map(categoryDTO,Category.class);
        Category savedCat= categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCat!=null){
            throw new ApiException("category with name "+category.getCategoryName()+" already exist");
        }
//        Category category=mapper.map(categoryDTO,Category.class);
       Category savedCategory=categoryRepository.save(category);

       return mapper.map(savedCategory,CategoryDTO.class);


    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category savedCategory =categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        categoryRepository.delete(savedCategory);
        return mapper.map(savedCategory,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO,Long categoryID) {
        Optional<Category> savedCategoryOptional=categoryRepository.findById(categoryID);

        Category savedCategory= savedCategoryOptional
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryID));
        Category category=mapper.map(categoryDTO,Category.class);
        category.setCategoryId(categoryID);

        categoryRepository.save(category);


        return mapper.map(category,CategoryDTO.class);


    }

}
