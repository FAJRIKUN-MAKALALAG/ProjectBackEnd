package com.Project_backend.Service;

import com.Project_backend.Entity.Order;
import com.Project_backend.Entity.Product;
import com.Project_backend.Entity.User;
import com.Project_backend.Repository.OrderRepository;
import com.Project_backend.Repository.ProductRepository;
import com.Project_backend.Repository.UserRepository;
import com.Project_backend.dto.OrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<?> createOrder(Order orderRequest) {
        Optional<Product> productOpt = productRepository.findById(orderRequest.getProduct().getId());
        if (productOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Produk tidak ditemukan!");
        }

        Optional<User> userOpt = userRepository.findById(orderRequest.getUser().getId());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User tidak ditemukan!");
        }

        Product product = productOpt.get();
        Order order = new Order();
        order.setProduct(product);
        order.setUser(userOpt.get());
        order.setQuantity(orderRequest.getQuantity());
        order.setDateEstimation(orderRequest.getDateEstimation());
        order.setOrderDate(new Date());
        order.setTotalPrice(product.getPrice() * orderRequest.getQuantity());

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }

    public ResponseEntity<?> getOrderDetail(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOpt.get();

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setQuantity(order.getQuantity());
        response.setDateEstimation(order.getDateEstimation());
        response.setTotalPrice(order.getTotalPrice());
        response.setProduct(order.getProduct());
        response.setUser(order.getUser());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<Order>> listOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }
}
