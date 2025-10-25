package com.hyuri.kanji_study.domain.sentence.service;

import com.hyuri.kanji_study.domain.sentence.dto.KunSentenceDto;
import com.hyuri.kanji_study.domain.sentence.dto.OnSentenceDto;
import com.hyuri.kanji_study.domain.sentence.entity.KunSentenceEntity;
import com.hyuri.kanji_study.domain.sentence.entity.OnSentenceEntity;
import com.hyuri.kanji_study.domain.sentence.repository.KunSentenceRepository;
import com.hyuri.kanji_study.domain.sentence.repository.OnSentenceRepository;
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
public class SentenceServiceImpl implements SentenceService {

    private final KunSentenceRepository kunSentRepo;
    private final OnSentenceRepository onSentRepo;

    // kunSentence
    @Override
    public KunSentenceDto readByKunSenId(Long id) {
        KunSentenceEntity ks = kunSentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("KunSentence not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new KunSentenceDto(
                ks.getId(),
                ks.getKanji().getId(),
                ks.getKunJpText(),
                ks.getKunKrText()
        );
    }

    @Override
    public List<KunSentenceDto> getKunSenListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<KunSentenceEntity> entityList = kunSentRepo.findAll(sort);

        List<KunSentenceDto> dtoList = new ArrayList<>();
        for (KunSentenceEntity entity : entityList) {
            KunSentenceDto dto = KunSentenceDto.builder()
                    .id(entity.getId())
                    .kanji_id(entity.getKanji().getId())
                    .kunJpText(entity.getKunJpText())
                    .kunKrText(entity.getKunKrText())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }

    // onSentence
    @Override
    public OnSentenceDto readByOnSenId(Long id) {
        OnSentenceEntity os = onSentRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OnSentence not found: " + id));

        // 간단히 DTO 변환 (필요시 확장 가능)
        return new OnSentenceDto(
                os.getId(),
                os.getKanji().getId(),
                os.getOnJpText(),
                os.getOnKrText()
        );
    }

    @Override
    public List<OnSentenceDto> getOnSenListAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<OnSentenceEntity> entityList = onSentRepo.findAll(sort);

        List<OnSentenceDto> dtoList = new ArrayList<>();
        for (OnSentenceEntity entity : entityList) {
            OnSentenceDto dto = OnSentenceDto.builder()
                    .id(entity.getId())
                    .kanji_id(entity.getKanji().getId())
                    .onJpText(entity.getOnJpText())
                    .onKrText(entity.getOnKrText())
                    .build();
            dtoList.add(dto);
        }

        return dtoList;
    }
}
