// src/main/java/com/hyuri/kanji_study/controller/KanjiPageController.java
package com.hyuri.kanji_study.controller;

import com.hyuri.kanji_study.dto.KanjiDto;
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
@RequestMapping("kanji")
public class KanjiPageController {

    private final KanjiService kanjiService;

    @GetMapping("/list")
    public List<KanjiDto> list() {
        log.info(">>> GET /kanji/list");
        List<KanjiDto> list = kanjiService.getListAll();
        log.debug("result size = {}", list.size());
        return list; // JSON으로 응답
    }
}