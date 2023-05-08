package com.example.coursework.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SupplierDto {
    String id;
    String companyName;
    String contactPerson;
    String address;
    String email;
}
