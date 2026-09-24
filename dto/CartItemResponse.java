package com.example.ecom_Application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {
    private String id;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
