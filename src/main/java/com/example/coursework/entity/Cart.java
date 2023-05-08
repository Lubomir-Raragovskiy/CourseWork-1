package com.example.coursework.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cart implements Comparable {
    private int id;
    private float totalCost;
    private int totalAmount;
    private List<Product> products;

    @Override
    public int compareTo(Object o) {
        Cart cart = (Cart) o;
        return Integer.compare(this.getId(), cart.getId());
    }
}
