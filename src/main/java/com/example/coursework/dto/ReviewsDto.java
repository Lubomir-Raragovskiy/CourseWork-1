package com.example.coursework.dto;

import com.example.coursework.entity.Product;
import com.example.coursework.entity.User;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReviewsDto {
    String id;
    String userId;
    String productId;
    String text;
    String rate;
}
