package com.Project_backend.dto;

import com.Project_backend.Entity.Product;
import com.Project_backend.Entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class OrderResponse {
    private Long id;
    private Date orderDate;
    private Double totalPrice;
    private Date dateEstimation;
    private Integer quantity;
    private Product product;
    private User user;

    public Long getProductId() {
        return (product != null) ? product.getId() : null;
    }

    public Object getTotalPrice() {
        return (totalPrice != null && totalPrice % 1 == 0) ? totalPrice.intValue() : totalPrice;
    }
}
