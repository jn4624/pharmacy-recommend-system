package com.exam.app.direction.service

import com.exam.app.api.dto.DocumentDTO
import com.exam.app.pharmacy.dto.PharmacyDTO
import com.exam.app.pharmacy.service.PharmacySearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService = Mock()

    private DirectionService directionService = new DirectionService(pharmacySearchService)

    private List<PharmacyDTO> pharmacyList

    def setup() {
        pharmacyList = new ArrayList<>()
        pharmacyList.addAll(
                PharmacyDTO.builder()
                    .id(1L)
                    .pharmacyName("돌곶이온누리약국")
                    .pharmacyAddress("주소1")
                    .longitude(127.0569046)
                    .latitude(37.61040424)
                    .build(),
                PharmacyDTO.builder()
                    .id(2L)
                    .pharmacyName("호수온누리약국")
                    .pharmacyAddress("주소2")
                    .longitude(127.029052)
                    .latitude(37.60894036)
                    .build()
        )
    }

    def "buildDirectionList - 결과값이 거리 순으로 정렬되는지 확인"() {
        given:
        def addressName = "서울 성북구 종암로10길"
        double inputLongitude = 127.037033003036
        double inputLatitude = 37.5960650456809

        def documentDto = DocumentDTO.builder()
                .addressName(addressName)
                .longitude(inputLongitude)
                .latitude(inputLatitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList

        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetPharmacyName == "호수온누리약국"
        results.get(1).targetPharmacyName == "돌곶이온누리약국"
    }

    def "buildDirectionList - 정해진 반경 10km 내로 검색되는지 확인"() {
        given:
        pharmacyList.add(
                PharmacyDTO.builder()
                    .id(3L)
                    .pharmacyName("경기약국")
                    .pharmacyAddress("주소3")
                    .longitude(127.236707811313)
                    .latitude(37.3825107393401)
                .build()
        )

        def addressName = "서울 성북구 종암로10길"
        double inputLongitude = 127.037033003036
        double inputLatitude = 37.5960650456809

        def documentDto = DocumentDTO.builder()
                .addressName(addressName)
                .longitude(inputLongitude)
                .latitude(inputLatitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyList

        def results = directionService.buildDirectionList(documentDto)

        then:
        results.size() == 2
        results.get(0).targetPharmacyName == "호수온누리약국"
        results.get(1).targetPharmacyName == "돌곶이온누리약국"
    }

}
