package com.hyuri.kanji_study.domain.sentence.service;

import com.hyuri.kanji_study.domain.sentence.dto.KunSentenceDto;
import com.hyuri.kanji_study.domain.sentence.dto.OnSentenceDto;

import java.util.List;

public interface SentenceService {

    // kunSentenceDto
    KunSentenceDto readByKunSenId(Long id);
    List<KunSentenceDto> getKunSenListAll();

    // onSentenceDto
    OnSentenceDto readByOnSenId(Long id);
    List<OnSentenceDto> getOnSenListAll();
}
