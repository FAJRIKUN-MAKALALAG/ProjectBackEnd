package com.Project_backend.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long productId;  // ID Produk
    private Integer quantity;  // Jumlah produk yang dipesan
    private Double price;  // Harga produk
}
