package com.hyuri.kanji_study.domain.sentence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnSentenceDto {
    private Long id;
    private Long kanji_id;
    private String onJpText;
    private String onKrText;
}
