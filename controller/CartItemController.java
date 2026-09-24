package com.example.ecom_Application.controller;

import com.example.ecom_Application.dto.CartItemRequest;
import com.example.ecom_Application.dto.CartItemResponse;
import com.example.ecom_Application.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemFromCart(@RequestHeader("UID") String userId, @PathVariable String id){
        Boolean result = cartItemService.deleteItemFromCart(userId,id);
        if(result){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping()
    public ResponseEntity<List<CartItemResponse>> fetchItemFromCart(@RequestHeader("UID") String id){
        List<CartItemResponse> response = cartItemService.fetchItemFromCart(id);
        if(response.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
