package com.hyuri.kanji_study.domain.saved.service;

import com.hyuri.kanji_study.domain.saved.dto.SaveVocabDto;

import java.util.List;

public interface SavedVocabService {
    SaveVocabDto saveVocab(String loginId, Long vocabId);
    List<SaveVocabDto> getSaveVocabList(String loginId);
    void removeVocabSave(String loginId, Long vocabId);
}
