package com.Project_backend.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference(value = "user-cart")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference(value = "product-cart")
    private Product product;

    private Integer quantity;

    @PrePersist
    @PreUpdate
    public void validateQuantity() {
        if (this.quantity == null || this.quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }
}
