package com.exam.app.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoApiResponseDTO {

    private MetaDTO metaDto;

    private List<DocumentDTO> documentList;

}
