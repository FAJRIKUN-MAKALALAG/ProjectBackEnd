package com.Project_backend.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date orderDate = new Date();

    @Column(nullable = false)
    private Double totalPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEstimation;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;
}
