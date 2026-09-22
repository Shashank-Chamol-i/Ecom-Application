package com.example.ecom_Application.service;

import com.example.ecom_Application.dto.ProductRequest;
import com.example.ecom_Application.dto.ProductResponse;
import com.example.ecom_Application.entity.Product;
import com.example.ecom_Application.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServices {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .category(productRequest.getCategory())
                .imageUrl(productRequest.getImageUrl())
                .build();
        product = productRepository.save(product);
        return mapProductToResponse(product);
    }

    public ProductResponse updateProduct(String id, ProductRequest productRequest) {
        Product product =  productRepository.findById(id).orElseThrow(()->new RuntimeException("No such Product Exist in Database : "));
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
         productRepository.save(product);
        return mapProductToResponse(product);
    }


    public ProductResponse mapProductToResponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
        response.setImageUrl(product.getImageUrl());
        response.setActive(product.getActive());
        return response;
    }


    public List<ProductResponse> getAllProducts() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapProductToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchProduct(String keyword) {
       return productRepository.searchProduct(keyword).stream()
               .map(this::mapProductToResponse)
               .collect(Collectors.toList());
    }

    public String deleteProductById(String id) {
        return productRepository.findById(id)
                .map(product ->{
                    product.setActive(false);
                    productRepository.save(product);
                    return "Product Removed Successfully : "+id ;
                })
                .orElseThrow(()->new RuntimeException("No such Product Exist in database : "));
    }
}
