package com.example.ecom_Application.service;

import com.example.ecom_Application.dto.CartItemRequest;
import com.example.ecom_Application.dto.CartItemResponse;
import com.example.ecom_Application.entity.CartItem;
import com.example.ecom_Application.entity.Product;
import com.example.ecom_Application.entity.User;
import com.example.ecom_Application.repository.CartRepository;
import com.example.ecom_Application.repository.ProductRepository;
import com.example.ecom_Application.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CartItemService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository ;
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

    @Transactional
    public  Boolean deleteItemFromCart(String userId,String productId){
      return userRepository.findById(userId).map(
              product->{
                  productRepository.findById(productId).orElseThrow(()->new RuntimeException("No such product Exist  :  "));
                  cartRepository.deleteByUserIdAndProductId(userId,productId);
                  return true;
              }
      ).orElseThrow(()->new RuntimeException("Removal Request not Processed : "));
    }

    public List<CartItemResponse> fetchItemFromCart(String userId){
        return userRepository.findById(userId).map(user->
                cartRepository.findByUserId(user.getId()).stream().map(this::mapToResponse)
        ).orElseThrow().toList();
    }

    public CartItemResponse mapToResponse(CartItem cartItem){
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setQuantity(cartItem.getQuantity());
        response.setPrice(cartItem.getPrice());
        return response;
    }
}
