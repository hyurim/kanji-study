// src/main/java/com/hyuri/kanji_study/controller/KanjiPageController.java
package com.hyuri.kanji_study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.hyuri.kanji_study.dto.KanjiDto;
import com.hyuri.kanji_study.service.KanjiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
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
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        KanjiDto dto = kanjiService.readById(id);
        model.addAttribute("kanji", dto);
        log.debug("dto = {}",dto);
        return "kanji-detail";
    }
}