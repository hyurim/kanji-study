// src/main/java/com/hyuri/kanji_study/controller/KanjiPageController.java
package com.hyuri.kanji_study.controller;

import org.springframework.ui.Model;
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
public class KanjiPageController {

    private final KanjiService kanjiService;

    @GetMapping("/list")
    public List<KanjiDto> list() {
        return kanjiService.getListAll();
    }

    @GetMapping("/{id}")
    public KanjiDto detail(@PathVariable Long id) {
        // 필요시 상세 DTO로 교체 가능
        return kanjiService.readById(id);
    }

}