package com.gamesup.api.controller;

import com.gamesup.api.dto.order.*;
import com.gamesup.api.response.ApiResponse;
import com.gamesup.api.security.UserFromApi;
import com.gamesup.api.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderDto>> createOrder(
            @RequestBody CreateOrderDto dto,
            @AuthenticationPrincipal UserFromApi user
    ) {
        OrderDto order = orderService.createOrder(user.getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), "Order created successfully", order));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderDto>>> getUserOrders(
            @AuthenticationPrincipal UserFromApi user
    ) {
        List<OrderDto> orders = orderService.getOrdersByUser(user.getId());
        return ResponseEntity.ok(new ApiResponse<>(200, "Orders fetched successfully", orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrder(
            @PathVariable Long id,
            @AuthenticationPrincipal UserFromApi user
    ) {
        OrderDto order = orderService.getOrderById(id, user.getId());
        return ResponseEntity.ok(new ApiResponse<>(200, "Order fetched successfully", order));
    }
}
