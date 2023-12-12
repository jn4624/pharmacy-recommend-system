package com.exam.app.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDTO {

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("x")
    private double longitude;

    @JsonProperty("y")
    private double latitude;

}
