package com.hyuri.kanji_study.service;

import com.hyuri.kanji_study.dto.KanjiDto;
import com.hyuri.kanji_study.dto.KunyomiDto;
import com.hyuri.kanji_study.entity.KanjiEntity;
import com.hyuri.kanji_study.entity.KunyomiEntity;
import com.hyuri.kanji_study.repository.KanjiRepository;
import com.hyuri.kanji_study.repository.KunSentenceRepository;
import com.hyuri.kanji_study.repository.KunyomiRepository;
import com.hyuri.kanji_study.repository.OnSentenceRepository;
import com.hyuri.kanji_study.repository.OnyomiRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class KanjiServiceImpl implements KanjiService {

    private final KanjiRepository kanjiRepo;
    private final KunyomiRepository kunRepo;
    private final KunSentenceRepository kunSentRepo;
    private final OnyomiRepository onRepo;
    private final OnSentenceRepository onSentRepo;

    // kanji
    @Override
    public KanjiDto readByKanjiId(Long id) {
        KanjiEntity k = kanjiRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kanji not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new KanjiDto(
                k.getId(),
                k.getGlyph(),
                k.getMeaning(),
                k.getKunyomi(),
                k.getOnyomi()
        );
    }
    @Override
    public List<KanjiDto> getKanjiListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<KanjiEntity> entityList = kanjiRepo.findAll(sort);

        // entity 데이터를 dto 데이터로 옮겨담기
        List<KanjiDto> dtoList = new ArrayList<>();
        for (KanjiEntity entity : entityList) {
            KanjiDto dto = KanjiDto.builder()
                    .id(entity.getId())
                    .glyph(entity.getGlyph())
                    .meaning(entity.getMeaning())
                    .kunyomi(entity.getKunyomi())
                    .onyomi(entity.getOnyomi())
                    .build();
            dtoList.add(dto);
            log.debug("DTO = {}", dto);
        }

        // dto 리턴
        return dtoList;
    }


    // kunyomi
    @Override
    public KunyomiDto readByKunyomiId(Long id) {
        KunyomiEntity ky = kunRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kunyomi not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new KunyomiDto(
                ky.getId(),
                ky.getKanji().getId(),
                ky.getKunGlyph(),
                ky.getKunKana(),
                ky.getKunMeaning()
        );
    }

    @Override
    public List<KunyomiDto> getKunyomiListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<KunyomiEntity> entityList = kunRepo.findAll(sort);

        List<KunyomiDto> dtoList = new ArrayList<>();
        for (KunyomiEntity entity : entityList) {
            KunyomiDto dto = KunyomiDto.builder()
                    .id(entity.getId())
                    .kanji_id(entity.getKanji().getId())
                    .kunGlyph(entity.getKunGlyph())
                    .kunKana(entity.getKunKana())
                    .kunMeaning(entity.getKunMeaning())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    // kunSentence

    // onyomi

    // onSentence
}
