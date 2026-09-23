package com.example.ecom_Application.controller;

import com.example.ecom_Application.dto.CartItemRequest;
import com.example.ecom_Application.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/api/ecom/cart")
@Controller
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping("/create")
    public ResponseEntity<String> addToCart(@RequestHeader("UID")String userId, @RequestBody  CartItemRequest request){
        Boolean result =  cartItemService.addProduct(userId,request);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).body("Product added successfully");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not added successfully");
        }


    }
}
