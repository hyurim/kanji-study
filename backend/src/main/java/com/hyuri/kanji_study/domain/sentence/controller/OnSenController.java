package com.hyuri.kanji_study.domain.sentence.controller;

import com.hyuri.kanji_study.domain.sentence.dto.OnSentenceDto;
import com.hyuri.kanji_study.domain.sentence.service.SentenceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/onSentence")
public class OnSenController {

    private final SentenceService sentenceService;

    @GetMapping("/list")
    public List<OnSentenceDto> list() {
        return sentenceService.getOnSenListAll();
    }

    @GetMapping("/{id}")
    public OnSentenceDto getOnSen(@PathVariable Long id) {
        return sentenceService.readByOnSenId(id);
    }
}