package com.Project_backend.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private Date orderDate;
    private Date dateEstimation;
    private Double totalPrice;
    private Integer quantity;
    private String status;
    private List<ProductOrderDto> products;
}
