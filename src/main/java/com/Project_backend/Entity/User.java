package com.Project_backend.Entity;

import com.Project_backend.Entity.Biodata;
import com.Project_backend.Entity.Cart;
import com.Project_backend.Entity.Payment;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Biodata biodata;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cart> cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payment;

    // Constructor dengan role default "USER"
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = Role.USER;  // Default role USER
        this.cart = new ArrayList<>();
        this.payment = new ArrayList<>();
    }

    // Constructor untuk registrasi
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;  // Default role USER
        this.cart = new ArrayList<>();
        this.payment = new ArrayList<>();
    }

    public enum Role {
        ADMIN,
        USER
    }
}
