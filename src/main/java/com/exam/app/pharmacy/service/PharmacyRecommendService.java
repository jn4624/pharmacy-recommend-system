package com.exam.app.pharmacy.service;

import com.exam.app.api.dto.DocumentDTO;
import com.exam.app.api.dto.KakaoApiResponseDTO;
import com.exam.app.api.service.KakaoAddressSearchService;
import com.exam.app.direction.dto.OutputDTO;
import com.exam.app.direction.entity.Direction;
import com.exam.app.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PharmacyRecommendService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public List<OutputDTO> recommendPharmacyList(String address) {
        KakaoApiResponseDTO kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if (ObjectUtils.isEmpty(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[PharmacyRecommendService recommendPharmacyList fail] Input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDTO documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        // 공공기관에서 제공 받은 데이터 사용
        // List<Direction> directionList = directionService.buildDirectionList(documentDto);
        // 카테고리로 장소 검색 kakao api 사용
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);

        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDTO)
                .collect(Collectors.toList());
    }

    private OutputDTO convertToOutputDTO(Direction direction) {
        return OutputDTO.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyAddress(direction.getTargetAddress())
                .directionUrl("todo")
                .roadViewUrl("todo")
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }

}
