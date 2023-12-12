package com.exam.app.direction.entity;

import com.exam.app.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "direction")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Direction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 고객 주소 정보
    private String inputAddress;
    private double inputLongitude;
    private double inputLatitude;

    // 약국 정보
    private String targetPharmacyName;
    private String targetAddress;
    private double targetLongitude;
    private double targetLatitude;

    // 고객 주소와 약국 주소 사이의 거리
    private double distance;

}
