package com.Project_backend.dto;

import lombok.Data;

@Data
public class ProductOrderDto {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
}
