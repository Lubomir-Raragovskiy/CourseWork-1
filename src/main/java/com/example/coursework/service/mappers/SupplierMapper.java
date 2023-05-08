package com.example.coursework.service.mappers;

import com.example.coursework.dto.SupplierDto;
import com.example.coursework.entity.Supplier;

public class SupplierMapper {

    public static Supplier mapToSupplier(SupplierDto supplierDto){
        return Supplier.builder()
                .id(Integer.parseInt(supplierDto.getId()))
                .companyName(supplierDto.getCompanyName())
                .contactPerson(supplierDto.getContactPerson())
                .address(supplierDto.getAddress())
                .email(supplierDto.getEmail())
                .build();
    }
}
