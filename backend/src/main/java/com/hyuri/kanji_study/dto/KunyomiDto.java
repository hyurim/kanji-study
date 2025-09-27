package com.hyuri.kanji_study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KunyomiDto {
    private Long id;
    private Long kanji_id;
    private String kunGlyph;
    private String kunKana;
    private String kunMeaning;
}
