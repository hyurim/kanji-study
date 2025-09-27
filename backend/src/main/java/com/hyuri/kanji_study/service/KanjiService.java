package com.hyuri.kanji_study.service;

import com.hyuri.kanji_study.dto.KanjiDto;
import com.hyuri.kanji_study.dto.KunyomiDto;

import java.util.List;

public interface KanjiService {
    // kanjiDto
    KanjiDto readByKanjiId(Long id);
    List<KanjiDto> getKanjiListAll();

    // kunyomiDto
    KunyomiDto readByKunyomiId(Long id);
    List<KunyomiDto> getKunyomiListAll();
    // kunSentenceDto

    // onyomiDto

    // onSentenceDto
}
