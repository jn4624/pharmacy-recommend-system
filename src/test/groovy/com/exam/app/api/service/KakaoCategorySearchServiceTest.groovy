package com.exam.app.api.service

import com.exam.app.AbstractIntegrationContainerBaseTest
import com.exam.app.api.dto.DocumentDTO
import org.springframework.beans.factory.annotation.Autowired

class KakaoCategorySearchServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private KakaoCategorySearchService kakaoCategorySearchService;

    def "requestPharmacyCategorySearch - 정해진 반경 내에 최대 검색 갯수를 반환한다"() {
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
        def results = kakaoCategorySearchService.requestPharmacyCategorySearch(inputLongitude, inputLatitude, 10.0)

        then:
        results.documentList.size() > 0
    }

}
