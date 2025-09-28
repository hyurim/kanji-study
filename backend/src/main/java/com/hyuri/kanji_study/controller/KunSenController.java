package com.hyuri.kanji_study.controller;

import com.hyuri.kanji_study.dto.KunSentenceDto;
import com.hyuri.kanji_study.service.KanjiService;
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
@RequestMapping("/api/kunSent")
public class KunSenController {

    private final KanjiService kanjiService;

    @GetMapping("/list")
    public List<KunSentenceDto> list() {
        return kanjiService.getKunSenListAll();
    }

    @GetMapping("/{id}")
    public KunSentenceDto getKunyomi(@PathVariable Long id) {
        return kanjiService.readByKunSenId(id);
    }

}