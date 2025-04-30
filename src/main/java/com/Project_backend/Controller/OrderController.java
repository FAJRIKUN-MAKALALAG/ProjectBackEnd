package com.Project_backend.Controller;

import com.Project_backend.Service.OrderService;
import com.Project_backend.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // Buat order dari cart
    @PostMapping("/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId,
                                         @RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(userId, orderRequestDto);
    }

    // Ambil semua order
    @GetMapping
    public ResponseEntity<?> listOrders() {
        return orderService.listOrders();
    }

    // Ambil detail order berdasarkan ID
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetail(@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }

    // Ambil semua order milik user
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }
}
