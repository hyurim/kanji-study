package com.hyuri.kanji_study.controller;

import com.hyuri.kanji_study.dto.KanjiDto;
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
@RequestMapping("/api/kanji")
public class KanjiController {

    private final KanjiService kanjiService;

    @GetMapping("/list")
    public List<KanjiDto> list() {
        return kanjiService.getKanjiListAll();
    }

    @GetMapping("/{id}")
    public KanjiDto getKanji(@PathVariable Long id) {
        return kanjiService.readByKanjiId(id);
    }


}