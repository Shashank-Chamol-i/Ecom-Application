package com.example.ecom_Application.repository;

import com.example.ecom_Application.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItem,String> {

    CartItem findByUserIdAndProductId(String userId,String productId);
    void deleteByUserIdAndProductId(String userId,String productId);
    List<CartItem> findByUserId(String userId);
}
