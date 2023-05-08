package com.example.coursework.entity;

import com.example.coursework.entity.enums.PeymentMethod;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Payment implements Comparable{
    int id;
    PeymentMethod peymentMethod;
    double amount;
    Order order;

    @Override
    public int compareTo(Object o) {
        Payment payment = (Payment) o;
        return Integer.compare(this.getId(),payment.getId());
    }
}
