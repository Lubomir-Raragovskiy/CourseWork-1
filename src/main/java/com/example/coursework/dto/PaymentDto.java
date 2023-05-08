package com.example.coursework.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PaymentDto {
    String id;
    String paymentMethod;
    String amount;
    String orderId;
}
