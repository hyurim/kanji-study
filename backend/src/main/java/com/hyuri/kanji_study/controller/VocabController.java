package com.hyuri.kanji_study.controller;

import com.hyuri.kanji_study.dto.JlptVocabDto;
import com.hyuri.kanji_study.service.KanjiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api")
public class VocabController {

    private final KanjiService kanjiService;

    @GetMapping("/vocab")
    public List<JlptVocabDto> getAllVocab() {
        return kanjiService.getAll();
    }
}
