package com.Project_backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

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

    private String paymentMethod; // "bank_transfer", "credit_card", "e-wallet"

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // "PENDING", "COMPLETED", "FAILED"

    private String transactionId;

    private Double amount;

    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date paymentDate;
}

