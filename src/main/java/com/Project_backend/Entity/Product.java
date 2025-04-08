package com.Project_backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;


    private double price;

    @Lob  // Menandakan bahwa ini adalah data besar (Large Object)
    @Column(columnDefinition = "LONGTEXT") // Menggunakan tipe data TEXT untuk menyimpan Base64
    private String imageBase64;
}
