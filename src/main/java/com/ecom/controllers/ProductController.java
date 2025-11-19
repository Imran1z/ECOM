package com.ecom.controllers;

import com.ecom.Model.Product;
import com.ecom.payload.ProductDTO;
import com.ecom.payload.ProductResponse;
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
    public ResponseEntity<ProductDTO> addProduct (@RequestBody ProductDTO productDTO,
                                                  @PathVariable Long categoryId){
        ProductDTO savedProductDTO = productService.addProduct(productDTO,categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductDTO);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> GetAllProducts(){
        ProductResponse productResponse= productService.getAllProducts();
        return  ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
        ProductResponse productResponse= productService.getProductsByCategory(categoryId);
        return  ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("/public/products/Keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable String keyword){
        ProductResponse productResponse= productService.getProductsByKeyword(keyword);
        return  ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,@PathVariable Long productId){
//        System.out.println(productDTO);
        ProductDTO savedProductDTO =productService.updateProduct(productDTO,productId);
        return new ResponseEntity<>(savedProductDTO,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO productDTO=productService.deleteProduct(productId);

        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
}
