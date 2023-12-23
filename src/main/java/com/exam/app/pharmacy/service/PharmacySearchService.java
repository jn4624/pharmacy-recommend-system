package com.exam.app.pharmacy.service;

import com.exam.app.pharmacy.cache.PharmacyRedisTemplateService;
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
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    // failover 적용
    public List<PharmacyDTO> searchPharmacyDtoList() {
        // redis
        List<PharmacyDTO> pharmacyDtoList = pharmacyRedisTemplateService.findAll();

        if (!pharmacyDtoList.isEmpty()) {
            log.info("redis findAll success");
            return pharmacyDtoList;
        }

        // database
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
