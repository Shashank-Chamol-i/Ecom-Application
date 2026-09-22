package com.example.ecom_Application.controller;

import com.example.ecom_Application.dto.ProductRequest;
import com.example.ecom_Application.dto.ProductResponse;
import com.example.ecom_Application.service.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/ecom/product")
@Controller
public class ProductController {
    private final ProductServices productServices;

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productServices.createProduct(productRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productServices.updateProduct(id,productRequest));
    }
    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(productServices.getAllProducts());
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(productServices.searchProduct(keyword));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(productServices.deleteProductById(id));
    }
}
