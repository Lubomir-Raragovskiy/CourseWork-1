package com.example.coursework.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CategoryDto {
    String id;
    String category;
}
