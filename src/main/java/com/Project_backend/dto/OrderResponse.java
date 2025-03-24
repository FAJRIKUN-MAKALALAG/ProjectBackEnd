package com.Project_backend.dto;

import com.Project_backend.Entity.Product;
import lombok.Data;

import java.util.Date;

@Data
public class OrderResponse {
    private Long id;
    private Date orderDate;
    private Double totalPrice;
    private Long productId;
    private Date dateEstimation;
    private Integer quantity;
    private Product product;

    public Object getTotalPrice() {
        return (totalPrice % 1 == 0) ? totalPrice.intValue() : totalPrice;
    }

    public Long getProductId() {
        return (product != null) ? product.getId() : null;
    }
}
