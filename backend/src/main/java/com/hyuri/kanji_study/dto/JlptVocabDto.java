package com.hyuri.kanji_study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JlptVocabDto {
    private Long vocaId;
    private String word;          // 단어 (例: 勉強)
    private String reading;       // 읽기 (例: べんきょう)
    private String meaningKr;     // 한국어 뜻
    private String jlptLevel;     // N1~N5
    private String partOfSpeech;  // 품사
    private String exampleJp;     // 예문 (일본어)
    private String exampleKr;     // 예문 번역
}
