package com.hyuri.kanji_study.service;

import com.hyuri.kanji_study.dto.KanjiDto;

import java.util.List;

public interface KanjiService {
    KanjiDto readById(Long id);
    List<KanjiDto> getListAll();
}
