package com.exam.app.pharmacy.cache

import com.exam.app.AbstractIntegrationContainerBaseTest
import com.exam.app.pharmacy.dto.PharmacyDTO
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRedisTemplateService pharmacyRedisTemplateService

    def setup() {
        pharmacyRedisTemplateService.findAll()
            .forEach(dto -> {
                pharmacyRedisTemplateService.delete(dto.getId()) e
            })
    }

    def "save success"() {
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"

        PharmacyDTO pharmacyDto = PharmacyDTO.builder()
                .id(1L)
                .pharmacyName(pharmacyName)
                .pharmacyAddress(pharmacyAddress)
                .build()

        when:
        pharmacyRedisTemplateService.save(pharmacyDto)
        List<PharmacyDTO> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id == 1L
        result.get(0).pharmacyName == pharmacyName
        result.get(0).pharmacyAddress == pharmacyAddress
    }

    def "save fail"() {
        given:
        PharmacyDTO pharmacyDto = PharmacyDTO.builder().build()

        when:
        pharmacyRedisTemplateService.save(pharmacyDto)
        List<PharmacyDTO> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 0
    }

    def "delete"() {
        given:
        String pharmacyName = "name"
        String pharmacyAddress = "address"

        PharmacyDTO pharmacyDto = PharmacyDTO.builder()
                .id(1L)
                .pharmacyName(pharmacyName)
                .pharmacyAddress(pharmacyAddress)
                .build()

        when:
        pharmacyRedisTemplateService.save(pharmacyDto)
        pharmacyRedisTemplateService.delete(pharmacyDto.getId())
        List<PharmacyDTO> result = pharmacyRedisTemplateService.findAll()

        then:
        result.size() == 0
    }

}
