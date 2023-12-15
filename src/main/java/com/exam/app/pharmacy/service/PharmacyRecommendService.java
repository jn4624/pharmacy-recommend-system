package com.exam.app.pharmacy.service;

import com.exam.app.api.dto.DocumentDTO;
import com.exam.app.api.dto.KakaoApiResponseDTO;
import com.exam.app.api.service.KakaoAddressSearchService;
import com.exam.app.direction.entity.Direction;
import com.exam.app.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendPharmacyList(String address) {
        KakaoApiResponseDTO kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if (ObjectUtils.isEmpty(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendService recommendPharmacyList fail] Input address: {}", address);
            return;
        }

        DocumentDTO documentDto = kakaoApiResponseDto.getDocumentList().get(0);
        List<Direction> directionList = directionService.buildDirectionList(documentDto);
        directionService.saveAll(directionList);
    }

}
