package com.Project_backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String paymentMethod; // bank_transfer, credit_card, e-wallet

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;

    private String transactionId;

    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate = new Date();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Jika tidak dibutuhkan, Cart bisa dihapus dari relasi
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private Double totalAmount;
}
