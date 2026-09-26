package com.example.ecom_Application.controller;

import com.example.ecom_Application.dto.OrderResponseDTO;
import com.example.ecom_Application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/api/ecom/order")
@Controller
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDTO> getOrderItems(@RequestHeader("UID") String id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(id));
    }
}
