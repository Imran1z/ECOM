package com.ecom.controllers;

import com.ecom.Model.Product;
import com.ecom.payload.ProductDTO;
import com.ecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct (@RequestBody Product product,
                                                  @PathVariable Long categoryId){
        ProductDTO productDTO = productService.addProduct(product,categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }
}
