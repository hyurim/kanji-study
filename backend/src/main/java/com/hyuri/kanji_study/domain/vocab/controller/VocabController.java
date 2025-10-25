package com.hyuri.kanji_study.domain.vocab.controller;

import com.hyuri.kanji_study.domain.vocab.dto.JlptVocabDto;
import com.hyuri.kanji_study.domain.vocab.service.VocabQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/vocab")
public class VocabController {

    private final VocabQueryService vocabQueryService;

    @GetMapping("/list")
    public List<JlptVocabDto> getAllVocab() {
        return vocabQueryService.getAll();
    }
}
