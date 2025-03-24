package com.Project_backend.Controller;

import com.Project_backend.Entity.Order;
import com.Project_backend.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOrderDetail(@PathVariable("id") Long id) {
        return orderService.getOrderDetail(id);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Order>> getAllOrders() {
        return orderService.listOrders();
    }
}
