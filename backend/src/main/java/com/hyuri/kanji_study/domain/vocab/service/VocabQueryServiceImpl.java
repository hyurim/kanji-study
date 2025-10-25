package com.hyuri.kanji_study.domain.vocab.service;

import com.hyuri.kanji_study.domain.vocab.dto.JlptVocabDto;
import com.hyuri.kanji_study.domain.vocab.entity.JlptVocabEntity;
import com.hyuri.kanji_study.domain.vocab.repository.JlptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class VocabQueryServiceImpl implements VocabQueryService {

    private final JlptRepository jlptRepo;
    // voca
    @Override
    public List<JlptVocabDto> getAll() {
        return jlptRepo.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    private JlptVocabDto toDto(JlptVocabEntity e) {
        JlptVocabDto dto = new JlptVocabDto();
        dto.setVocaId(e.getVocaId());
        dto.setWord(e.getWord());
        dto.setReading(e.getReading());
        dto.setMeaningKr(e.getMeaningKr());
        dto.setJlptLevel(e.getJlptLevel());
        dto.setPartOfSpeech(e.getPartOfSpeech());
        dto.setExampleJp(e.getExampleJp());
        dto.setExampleKr(e.getExampleKr());
        return dto;
    }
}
