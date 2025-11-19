package com.ecom.service;

import com.ecom.Model.Category;
import com.ecom.Model.Product;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.payload.ProductDTO;
import com.ecom.payload.ProductResponse;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        Product product=mapper.map(productDTO,Product.class);
        product.setImage("default.png");
        product.setCategory(category);
        product.setSpecialPrice(product.getPrice()-(product.getDiscount()*0.01)*product.getPrice());

        Product savedProduct =productRepository.save(product);
        return mapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products= productRepository.findAll();
        List<ProductDTO> productDTOS=products.stream().map(product -> mapper.map(product,ProductDTO.class)).toList();

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        List<Product> products= productRepository.findByCategory(category);
        List<ProductDTO> productDTOS=products.stream().map(product -> mapper.map(product,ProductDTO.class)).toList();

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        List<Product> products= productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%');
        List<ProductDTO> productDTOS=products.stream().map(product -> mapper.map(product,ProductDTO.class)).toList();

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO,Long productId) {
        Product savedProduct =productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","ProductId",productId));

        savedProduct.setProductName(productDTO.getProductName());
        savedProduct.setDescription(productDTO.getDescription());
        savedProduct.setQuantity(productDTO.getQuantity());
        savedProduct.setDiscount(productDTO.getDiscount());
        savedProduct.setPrice(productDTO.getPrice());
        savedProduct.setSpecialPrice(productDTO.getSpecialPrice());


        productRepository.save(savedProduct);


        return mapper.map(savedProduct,ProductDTO.class );
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product savedProduct = productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        productRepository.deleteById(productId);
        return mapper.map(savedProduct,ProductDTO.class);
    }
}
