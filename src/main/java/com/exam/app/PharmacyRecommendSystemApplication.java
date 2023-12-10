package com.exam.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PharmacyRecommendSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacyRecommendSystemApplication.class, args);
    }

}
