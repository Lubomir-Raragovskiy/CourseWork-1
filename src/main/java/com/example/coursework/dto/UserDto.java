package com.example.coursework.dto;

import com.example.coursework.entity.DetailInformation;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDto {
    String id;
    String email;
    String phoneNumber;
    String password;
    String firstName;
    String surname;
    String detailInformationId;
}
