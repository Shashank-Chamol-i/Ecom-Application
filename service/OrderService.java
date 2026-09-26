package com.example.ecom_Application.service;

import com.example.ecom_Application.dto.OrderItemDTO;
import com.example.ecom_Application.dto.OrderResponseDTO;
import com.example.ecom_Application.entity.CartItem;
import com.example.ecom_Application.entity.Order;
import com.example.ecom_Application.entity.OrderStatus;
import com.example.ecom_Application.entity.User;
import com.example.ecom_Application.repository.CartRepository;
import com.example.ecom_Application.repository.OrderRepository;
import com.example.ecom_Application.repository.ProductRepository;
import com.example.ecom_Application.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class OrderService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Transactional
    public OrderResponseDTO createOrder(String userId){
       User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("No such user Exist :"));
       List<CartItem> cartItemList  = cartRepository.findByUserId(user.getId());
       if(cartItemList == null)
           throw new IllegalArgumentException("Cart is Empty : ");

        BigDecimal totalAmount = cartItemList.stream()
                .filter(Objects::nonNull)
                .filter(item->item.getPrice()!=null && item.getQuantity()>0)
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .orderStatus(OrderStatus.CONFIRM)
                .build();
        Order savedOrder = orderRepository.save(order);

      OrderResponseDTO responseDTO =  mapItemToOrderResponseDTO(savedOrder,cartItemList);
        cartRepository.deleteAll(cartItemList);

        return responseDTO;

    }
    public OrderResponseDTO mapItemToOrderResponseDTO(Order order , List<CartItem> list){
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(order.getId());
        response.setOrderDate(order.getCreatedAt());
        response.setOrderStatus(order.getOrderStatus());
        response.setTotal(order.getTotalAmount());
        response.setItems(mapOrderItemToResponseDTO(list));
        return response;
    }
    public List<OrderItemDTO> mapOrderItemToResponseDTO(List<CartItem> list){
      return list.stream()
                .filter(Objects::nonNull)
                .map(cartItem->{
                    OrderItemDTO response = new OrderItemDTO();
                       productRepository.findById(cartItem.getProduct().getId())
                               .map(product->{
                                   response.setProductId(product.getId());
                                   response.setProductName(product.getName());
                                   response.setProductPrice(product.getPrice());
                                   return response;
                               });
                        response.setQuantity(cartItem.getQuantity());
                        response.setSubTotal(cartItem.getPrice());
            return response;

                }).collect(Collectors.toList());
    }
}
