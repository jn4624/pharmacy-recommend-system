package com.exam.app.pharmacy.service

import com.exam.app.AbstractIntegrationContainerBaseTest
import com.exam.app.pharmacy.entity.Pharmacy
import com.exam.app.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepositoryService pharmacyRepositoryService

    @Autowired
    private PharmacyRepository pharmacyRepository

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository update - dirty checking success"() {
        given:
        String inputAddress = "서울특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
            .pharmacyAddress(inputAddress)
            .pharmacyName(name)
            .build()

        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(entity.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == modifiedAddress
    }

    def "PharmacyRepository update - dirty checking fail"() {
        given:
        String inputAddress = "서울특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build()

        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddressWithoutTransaction(entity.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == inputAddress
    }

    def "self invocation"() {
        given:
        String address = "서울특별시 성북구 종암동"
        String name = "은혜 약국"
        double longitude = 128.11
        double latitude = 36.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .longitude(longitude)
                .latitude(latitude)
                .build()

        when:
        pharmacyRepositoryService.bar(Arrays.asList(pharmacy))

        then:
        def e = thrown(RuntimeException.class)
        def result = pharmacyRepositoryService.findAll()
        result.size() == 1 // 트랜잭션이 적용되지 않는다
    }

    def "transactional readOnly test"() {
        given:
        String inputAddress = "서울특별시 성북구"
        String modifiedAddress = "서울특별시 광진구"
        String name = "은혜 약국"
        double longitude = 128.11
        double latitude = 36.11

        def input = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .longitude(longitude)
                .latitude(latitude)
                .build()

        when:
        def pharmacy = pharmacyRepository.save(input)
        pharmacyRepositoryService.startReadOnlyMethod(pharmacy.id)

        then:
        def result = pharmacyRepositoryService.findAll()
        result.get(0).getPharmacyAddress() == inputAddress // dirty checking이 적용되지 않는다
    }

}
