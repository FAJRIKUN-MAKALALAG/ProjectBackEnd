package com.Project_backend.Service;

import com.Project_backend.Entity.*;
import com.Project_backend.Repository.*;
import com.Project_backend.dto.OrderItemDto;
import com.Project_backend.dto.OrderRequestDto;
import com.Project_backend.dto.OrderResponseDto;
import com.Project_backend.dto.ProductOrderDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final UserRepository userRepository;
    private  final CartRepository cartRepository;

    @Transactional
    public ResponseEntity<?> createOrder(Long userId, OrderRequestDto orderRequestDto) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User tidak ditemukan!");
        }

        User user = userOpt.get();

        List<Cart> carts = cartRepository.findByUserId(userId);
        if (carts.isEmpty()) {
            return ResponseEntity.badRequest().body("Keranjang kosong!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setDateEstimation(orderRequestDto.getDateEstimation());
        order.setStatus(OrderStatus.PENDING);

        double totalPrice = 0.0;
        int totalQuantity = 0;
        List<OrderProduct> orderProducts = new ArrayList<>();

        for (Cart cart : carts) {
            Product product = cart.getProduct();
            if (product == null) {
                return ResponseEntity.badRequest().body("Produk tidak ditemukan dalam cart.");
            }

            int quantity = cart.getQuantity();
            if (quantity > product.getStock()) {
                return ResponseEntity.badRequest().body("Stok tidak cukup untuk produk: " + product.getName());
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(quantity);
            orderProduct.setPrice(product.getPrice());
            orderProducts.add(orderProduct);

            product.setStock(product.getStock() - quantity);
            productRepository.save(product);

            totalPrice += product.getPrice() * quantity;
            totalQuantity += quantity;
        }

        order.setTotalPrice(totalPrice);
        order.setQuantity(totalQuantity);
        order.setOrderProducts(orderProducts); // Hubungkan list orderProduct ke order
        Order savedOrder = orderRepository.save(order);

        // Simpan semua orderProduct
        orderProductRepository.saveAll(orderProducts);

        cartRepository.deleteAll(carts); // Kosongkan cart

        OrderResponseDto responseDto = convertToResponseDto(savedOrder);
        return ResponseEntity.ok(responseDto);
    }
    public ResponseEntity<?> getOrderDetail(Long orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Order tidak ditemukan!");
        }

        Order order = orderOpt.get();
        OrderResponseDto responseDto = convertToResponseDto(order);
        return ResponseEntity.ok(responseDto);
    }



    public ResponseEntity<List<OrderResponseDto>> listOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponseDto> dtos = orders.stream()
                .map(this::convertToResponseDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<List<OrderResponseDto>> getOrdersByUser(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        List<OrderResponseDto> dtos = orders.stream()
                .map(this::convertToResponseDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    private OrderResponseDto convertToResponseDto(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setOrderId(order.getId());
        responseDto.setOrderDate(order.getOrderDate());
        responseDto.setDateEstimation(order.getDateEstimation());
        responseDto.setTotalPrice(order.getTotalPrice());
        responseDto.setQuantity(order.getQuantity());
        responseDto.setStatus(order.getStatus().name());

        List<ProductOrderDto> productDtos = new ArrayList<>();
        for (OrderProduct op : order.getOrderProducts()) {
            ProductOrderDto dto = new ProductOrderDto();
            dto.setProductId(op.getProduct().getId());
            dto.setProductName(op.getProduct().getName());
            dto.setPrice(op.getPrice());
            dto.setQuantity(op.getQuantity());
            productDtos.add(dto);
        }

        responseDto.setProducts(productDtos);
        return responseDto;
    }
}
