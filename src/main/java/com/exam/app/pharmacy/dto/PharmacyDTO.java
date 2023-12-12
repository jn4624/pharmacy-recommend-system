package com.exam.app.pharmacy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PharmacyDTO {

    private Long id;
    private String pharmacyName;
    private String pharmacyAddress;
    private double longitude;
    private double latitude;

}
