package com.Project_backend.Entity;

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
    private User user;  // Menghubungkan cart dengan user

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;  // Menghubungkan cart dengan produk

    private Integer quantity;  // Jumlah produk yang dibeli

    // Validasi untuk memastikan jumlah produk tidak kurang dari 1
    @PrePersist
    @PreUpdate
    public void validateQuantity() {
        if (this.quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
    }
}
