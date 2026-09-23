package com.example.ecom_Application.service;

import com.example.ecom_Application.dto.CartItemRequest;
import com.example.ecom_Application.entity.CartItem;
import com.example.ecom_Application.entity.Product;
import com.example.ecom_Application.entity.User;
import com.example.ecom_Application.repository.CartRepository;
import com.example.ecom_Application.repository.ProductRepository;
import com.example.ecom_Application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@RequiredArgsConstructor
@Service
public class CartItemService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Boolean addProduct(String userId, CartItemRequest request){
       User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("No such user exist : "));
       Product product = productRepository.findById(request.getProduct().getId()).orElseThrow(()->new RuntimeException("No such product exist : "));
        CartItem existingCart = cartRepository.findByUserIdAndProductId(userId,product.getId());
        if(request.getQuantity()>product.getStockQuantity()){
            return false;
        }
        if(existingCart==null){
            CartItem cartItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(request.getQuantity())
                    .price(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())))
                    .build();
            cartRepository.save(cartItem);
        }else{
          if(existingCart.getQuantity()+request.getQuantity()>product.getStockQuantity()){
             return false;
          }else{
              existingCart.setQuantity(existingCart.getQuantity()+request.getQuantity());
              existingCart.setPrice(existingCart.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
              cartRepository.save(existingCart);
          }
        }
        return true;

    }
}
