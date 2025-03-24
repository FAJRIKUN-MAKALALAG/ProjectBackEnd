package com.Project_backend.Service;

import com.Project_backend.Entity.Order;
import com.Project_backend.Entity.Product;
import com.Project_backend.Repository.OrderRepository;
import com.Project_backend.Repository.ProductRepository;
import com.Project_backend.dto.OrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public ResponseEntity<?> createOrder(Order order) {
        Optional<Product> productOpt = productRepository.findById(order.getProductId());

        if (productOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Produk tidak ditemukan!");
        }

        Product product = productOpt.get();

        // Hitung total harga berdasarkan jumlah produk yang dipesan
        order.setTotalPrice(product.getPrice() * order.getQuantity());

        Order savedOrder = orderRepository.save(order);
        return ResponseEntity.ok(savedOrder);
    }


    public ResponseEntity<?> getOrderDetail(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);

        if (orderOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOpt.get();
        Optional<Product> productOpt = productRepository.findById(order.getProductId());

        if (productOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Produk tidak ditemukan untuk pesanan ini!");
        }

        Product product = productOpt.get();

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setOrderDate(order.getOrderDate());
        orderResponse.setQuantity(order.getQuantity());
        orderResponse.setProduct(product);
        orderResponse.setDateEstimation(order.getDateEstimation());
        orderResponse.setTotalPrice(order.getTotalPrice());

        return ResponseEntity.ok(orderResponse);
    }

    public ResponseEntity<List<Order>> listOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

}
