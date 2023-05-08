package com.example.coursework.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User implements Comparable{
    private int id;
    private String email;
    private String phoneNumber;
    private int password;
    private String firstName;
    private String surname;
    private DetailInformation detailInformation;

    @Override
    public int compareTo(Object o) {
        User user = (User) o;
        return Integer.compare(this.getId(),user.getId());
    }
}
