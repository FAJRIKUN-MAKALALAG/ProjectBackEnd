package com.Project_backend.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer age;
    private String email;
    private String password;

    @Lob
    @Column(columnDefinition = "TEXT", nullable = true)
    private String imagesBase64;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Biodata biodata;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> carts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;
}

