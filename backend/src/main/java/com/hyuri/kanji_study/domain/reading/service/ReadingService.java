package com.hyuri.kanji_study.domain.reading.service;

import com.hyuri.kanji_study.domain.reading.dto.KunyomiDto;
import com.hyuri.kanji_study.domain.reading.dto.OnyomiDto;

import java.util.List;

public interface ReadingService {
    // kunyomiDto
    KunyomiDto readByKunyomiId(Long id);
    List<KunyomiDto> getKunyomiListAll();

    // onyomiDto
    OnyomiDto readByOnyomiId(Long id);
    List<OnyomiDto> getOnyomiListAll();
}
