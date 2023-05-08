package com.example.coursework.dto;

import com.example.coursework.entity.Product;
import com.example.coursework.entity.User;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class OrderDto {
    String id;
    String state;
    String dateOrdered;
    String dateShifted;
    String productId;
    String userId;
}
