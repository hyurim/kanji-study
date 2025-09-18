package com.hyuri.kanji_study.service;

import com.hyuri.kanji_study.dto.KanjiDto;
import com.hyuri.kanji_study.entity.KanjiEntity;
import com.hyuri.kanji_study.repository.KanjiRepository;
import com.hyuri.kanji_study.repository.KunyomiRepository;
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
    private final OnyomiRepository onRepo;

    @Override
    public List<KanjiDto> getListAll() {
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

}
