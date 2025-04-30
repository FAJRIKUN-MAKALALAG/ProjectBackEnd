package com.Project_backend.Repository;

import com.Project_backend.Entity.Order;
import com.Project_backend.Entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // Cari order berdasarkan userId dan status
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
    List<Order> findByUserId(Long userId);

}
