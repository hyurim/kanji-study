package com.hyuri.kanji_study.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record KanjiDetailDto(Long id,
                             String glyph,
                             String meaning,
                             List<String> kunyomiKana,
                             List<String> onyomiKana,
                             List<String> kunyomiWords,
                             List<String> onyomiWords,
                             List<ReadingDto> readings,
                             OffsetDateTime createdAt,
                             OffsetDateTime updatedAt) {
}
