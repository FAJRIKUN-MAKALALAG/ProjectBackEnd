package com.Project_backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "orders") // pakai "orders" biar gak konflik dengan SQL keyword
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date orderDate = new Date();  // Otomatis saat objek dibuat

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEstimation;

    // Relasi ke Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Relasi ke User (biar kita tahu siapa yang order)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Optional: jika ingin punya akses langsung ke payment
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
}
