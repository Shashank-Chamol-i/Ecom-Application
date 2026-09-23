package com.example.ecom_Application.dto;

import com.example.ecom_Application.entity.Product;
import lombok.Data;

@Data
public class CartItemRequest {
    private Product product;
    private Integer quantity;
}
