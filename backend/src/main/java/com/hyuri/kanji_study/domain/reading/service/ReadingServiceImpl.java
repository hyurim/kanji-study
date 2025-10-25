package com.hyuri.kanji_study.domain.reading.service;

import com.hyuri.kanji_study.domain.reading.dto.KunyomiDto;
import com.hyuri.kanji_study.domain.reading.dto.OnyomiDto;
import com.hyuri.kanji_study.domain.reading.entity.KunyomiEntity;
import com.hyuri.kanji_study.domain.reading.entity.OnyomiEntity;
import com.hyuri.kanji_study.domain.reading.repository.KunyomiRepository;
import com.hyuri.kanji_study.domain.reading.repository.OnyomiRepository;
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
public class ReadingServiceImpl implements ReadingService{

    private final KunyomiRepository kunRepo;
    private final OnyomiRepository onRepo;

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

    // onyomi
    @Override
    public OnyomiDto readByOnyomiId(Long id) {
        OnyomiEntity oy = onRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Onyomi not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new OnyomiDto(
                oy.getId(),
                oy.getKanji().getId(),
                oy.getOnGlyph(),
                oy.getOnKana(),
                oy.getOnMeaning()
        );

    }

    @Override
    public List<OnyomiDto> getOnyomiListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<OnyomiEntity> entityList = onRepo.findAll(sort);

        List<OnyomiDto> dtoList = new ArrayList<>();
        for (OnyomiEntity entity : entityList) {
            OnyomiDto dto = OnyomiDto.builder()
                    .id(entity.getId())
                    .kanji_id(entity.getKanji().getId())
                    .onGlyph(entity.getOnGlyph())
                    .onKana(entity.getOnKana())
                    .onMeaning(entity.getOnMeaning())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }
}
