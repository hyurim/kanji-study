package com.hyuri.kanji_study.domain.kanji.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KanjiDto {
    private Long id;
    private String glyph;
    private String meaning;
    private String reading;
    private String kunyomi;
    private String onyomi;

}
