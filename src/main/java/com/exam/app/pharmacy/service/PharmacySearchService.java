package com.exam.app.pharmacy.service;

import com.exam.app.pharmacy.dto.PharmacyDTO;
import com.exam.app.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacySearchService {

    private final PharmacyRepositoryService pharmacyRepositoryService;

    public List<PharmacyDTO> searchPharmacyDtoList() {
        return pharmacyRepositoryService.findAll()
                .stream()
                .map(this::convertToPharmacyDTO)
                .collect(Collectors.toList());
    }

    private PharmacyDTO convertToPharmacyDTO(Pharmacy pharmacy) {
        return PharmacyDTO.builder()
                .id(pharmacy.getId())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .pharmacyName(pharmacy.getPharmacyName())
                .longitude(pharmacy.getLongitude())
                .latitude(pharmacy.getLatitude())
                .build();
    }

}
