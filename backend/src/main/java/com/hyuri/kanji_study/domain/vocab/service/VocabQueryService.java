package com.hyuri.kanji_study.domain.vocab.service;

import com.hyuri.kanji_study.domain.vocab.dto.JlptVocabDto;

import java.util.List;

public interface VocabQueryService {
    List<JlptVocabDto> getAll();
}
