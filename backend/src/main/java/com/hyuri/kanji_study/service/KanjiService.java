package com.hyuri.kanji_study.service;

import com.hyuri.kanji_study.dto.KanjiDto;
import com.hyuri.kanji_study.dto.KunSentenceDto;
import com.hyuri.kanji_study.dto.KunyomiDto;
import com.hyuri.kanji_study.dto.OnSentenceDto;
import com.hyuri.kanji_study.dto.OnyomiDto;

import java.util.List;

public interface KanjiService {
    // kanjiDto
    KanjiDto readByKanjiId(Long id);
    List<KanjiDto> getKanjiListAll();

    // kunyomiDto
    KunyomiDto readByKunyomiId(Long id);
    List<KunyomiDto> getKunyomiListAll();

    // kunSentenceDto
    KunSentenceDto readByKunSenId(Long id);
    List<KunSentenceDto> getKunSenListAll();

    // onyomiDto
    OnyomiDto readByOnyomiId(Long id);
    List<OnyomiDto> getOnyomiListAll();

    // onSentenceDto
    OnSentenceDto readByOnSenId(Long id);
    List<OnSentenceDto> getOnSenListAll();
}
