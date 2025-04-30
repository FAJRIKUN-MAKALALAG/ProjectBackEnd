package com.Project_backend.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderRequestDto {
    private Long userId;
    private Date dateEstimation;
    private List<OrderItemDto> items;
}
