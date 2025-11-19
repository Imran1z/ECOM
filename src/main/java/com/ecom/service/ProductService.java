package com.ecom.service;

import com.ecom.Model.Product;
import com.ecom.payload.ProductDTO;
import com.ecom.payload.ProductResponse;
import org.springframework.stereotype.Service;

public interface ProductService {
    ProductDTO addProduct(Product product, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategory(Long categoryId);
}
