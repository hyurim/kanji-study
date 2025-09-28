package com.hyuri.kanji_study.controller;

import com.hyuri.kanji_study.dto.OnyomiDto;
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
@RequestMapping("/api/onyomi")
public class OnyomiController {

    private final KanjiService kanjiService;

    @GetMapping("/list")
    public List<OnyomiDto> list() {
        return kanjiService.getOnyomiListAll();
    }

    @GetMapping("/{id}")
    public OnyomiDto getOunyomi(@PathVariable Long id) {
        return kanjiService.readByOnyomiId(id);
    }

}