package com.ecom.service;

import com.ecom.Model.Category;
import com.ecom.Model.Product;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.payload.ProductDTO;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        product.setImage("default.png");
        product.setCategory(category);
        product.setSpecialPrice(product.getPrice()-(product.getDiscount()*0.01)*product.getPrice());

        Product savedProduct =productRepository.save(product);
        return mapper.map(savedProduct,ProductDTO.class);
    }
}
