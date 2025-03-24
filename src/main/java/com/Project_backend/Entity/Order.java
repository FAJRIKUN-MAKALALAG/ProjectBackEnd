package com.Project_backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date orderDate = new Date();  // Default: waktu saat order dibuat

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private Long productId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEstimation;

    @Column(nullable = false)
    private Integer quantity;
}
