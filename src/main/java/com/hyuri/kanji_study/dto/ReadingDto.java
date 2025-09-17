package com.hyuri.kanji_study.dto;

public record ReadingDto(Long id,
                         ReadingTypeDTO type,
                         String kana,
                         String word) {
}
