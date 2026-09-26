package com.example.ecom_Application.dto;
import com.example.ecom_Application.entity.OrderStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderResponseDTO {
    private String id;
    private List<OrderItemDTO> items;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
}
