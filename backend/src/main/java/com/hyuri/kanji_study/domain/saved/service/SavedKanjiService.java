package com.hyuri.kanji_study.domain.saved.service;

import com.hyuri.kanji_study.domain.saved.dto.SaveKanjiDto;

import java.util.List;

public interface SavedKanjiService {
    SaveKanjiDto addKanjiSave(String loginId, Long kanjiId);
    List<SaveKanjiDto> getSaveKanjiList(String loginId);
    void removeKanjiSave(String loginId, Long kanjiId);
}
