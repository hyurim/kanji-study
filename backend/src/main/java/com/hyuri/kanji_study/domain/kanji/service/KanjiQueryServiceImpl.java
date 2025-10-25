package com.hyuri.kanji_study.domain.kanji.service;

import com.hyuri.kanji_study.domain.kanji.dto.KanjiDto;
import com.hyuri.kanji_study.domain.kanji.entity.KanjiEntity;
import com.hyuri.kanji_study.domain.kanji.repository.KanjiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class KanjiQueryServiceImpl implements KanjiQueryService {

    private final KanjiRepository kanjiRepo;

    @Override
    public KanjiDto readByKanjiId(Long id) {
        KanjiEntity k = kanjiRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Kanji not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new KanjiDto(
                k.getId(),
                k.getGlyph(),
                k.getMeaning(),
                k.getReading(),
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
                    .reading(entity.getReading())
                    .kunyomi(entity.getKunyomi())
                    .onyomi(entity.getOnyomi())
                    .build();
            dtoList.add(dto);
            log.debug("DTO = {}", dto);
        }

        // dto 리턴
        return dtoList;
    }
}
