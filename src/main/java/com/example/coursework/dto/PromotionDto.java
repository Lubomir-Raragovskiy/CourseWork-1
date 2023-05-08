package com.example.coursework.dto;

import com.example.coursework.entity.Product;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class PromotionDto {
    String id;
    String startDate;
    String endDate;
    String productId;
    String discount;
}
