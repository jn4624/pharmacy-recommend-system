package com.exam.app.direction.controller;

import com.exam.app.direction.dto.InputDTO;
import com.exam.app.pharmacy.service.PharmacyRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FormController {

    private final PharmacyRecommendService pharmacyRecommendService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDTO inputDto) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList", pharmacyRecommendService.recommendPharmacyList(inputDto.getAddress()));

        return modelAndView;
    }

}
