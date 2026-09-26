package com.example.ecom_Application.repository;

import com.example.ecom_Application.entity.Order;
import com.example.ecom_Application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    User findByUser(String orderId);


}
