package com.Project_backend.dto;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Integer stock;
    private String imageBase64;
    private String categoryName; // untuk menampilkan nama kategori
}
