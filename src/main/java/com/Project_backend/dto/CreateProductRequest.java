package com.Project_backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreateProductRequest {
    private String name;
    private String description;
    private double price;
    private Integer stock;
    private String imageBase64;
    // Getter dan Setter
    // hanya nama kategori
    @Getter
    @Setter
    private String categoryName;  // Nama kategori yang ingin digunakan

}
