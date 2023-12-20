package com.exam.app.direction.controller

import com.exam.app.direction.dto.OutputDTO
import com.exam.app.pharmacy.service.PharmacyRecommendService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*

class FormControllerTest extends Specification {

    private MockMvc mockMvc
    private PharmacyRecommendService pharmacyRecommendService = Mock()
    private List<OutputDTO> outputDtoList

    def setup() {
        // FormController MockMvc 객체로 생성
        mockMvc = MockMvcBuilders.standaloneSetup(new FormController(pharmacyRecommendService)).build()

        outputDtoList = new ArrayList<>()
        outputDtoList.addAll(
                OutputDTO.builder()
                    .pharmacyName("pharmacy1")
                    .build(),
                OutputDTO.builder()
                    .pharmacyName("pharmacy2")
                    .build()
        )
    }

    def "GET /"() {
        expect:
        // FormController의 "/" URI를 get 방식으로 호출
        mockMvc.perform(get("/"))
            .andExpect(handler().handlerType(FormController.class))
            .andExpect(handler().methodName("main"))
            .andExpect(status().isOk())
            .andExpect(view().name("main"))
            .andDo(log())
    }

    def "POST /search"() {
        given:
        String inputAddress = "서울 성북구 종암동"

        when:
        def resultActions = mockMvc.perform(post("/search")
            .param("address", inputAddress))

        then:
        1 * pharmacyRecommendService.recommendPharmacyList(argument -> {
            // mock 객체의 argument 검증
            assert argument == inputAddress
        }) >> outputDtoList

        resultActions
            .andExpect(status().isOk())
            .andExpect(view().name("output"))
            // model에 outputFormList라는 key가 존재하는지 검증
            .andExpect(model().attributeExists("outputFormList"))
            .andExpect(model().attribute("outputFormList", outputDtoList))
            .andDo(print())
    }

}
