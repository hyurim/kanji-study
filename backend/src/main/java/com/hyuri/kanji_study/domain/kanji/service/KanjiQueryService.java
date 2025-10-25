package com.hyuri.kanji_study.domain.kanji.service;

import com.hyuri.kanji_study.domain.kanji.dto.KanjiDto;

import java.util.List;

public interface KanjiQueryService {
    KanjiDto readByKanjiId(Long id);
    List<KanjiDto> getKanjiListAll();
}
