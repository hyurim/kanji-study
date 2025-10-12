package com.hyuri.kanji_study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveVocabDto {
    private Long id;
    private Long userId;
    private Long vocabId;
}
