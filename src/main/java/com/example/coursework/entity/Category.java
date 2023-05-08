package com.example.coursework.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Category implements Comparable{
    int id;
    String category;
    List<Product> products;

    @Override
    public int compareTo(Object o) {
        Category category1 = (Category) o;
        return Integer.compare(this.getId(),category1.getId());
    }
}
