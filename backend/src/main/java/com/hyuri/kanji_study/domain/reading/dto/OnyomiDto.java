package com.hyuri.kanji_study.domain.reading.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnyomiDto {
    private Long id;
    private Long kanji_id;
    private String onGlyph;
    private String onKana;
    private String onMeaning;
}
