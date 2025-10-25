package com.hyuri.kanji_study.domain.reading.controller;

import com.hyuri.kanji_study.domain.reading.dto.KunyomiDto;
import com.hyuri.kanji_study.domain.reading.service.ReadingService;
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
@RequestMapping("/api/kunyomi")
public class KunyomiController {

    private final ReadingService readingService;

    @GetMapping("/list")
    public List<KunyomiDto> list() {
        return readingService.getKunyomiListAll();
    }

    @GetMapping("/{id}")
    public KunyomiDto getKunyomi(@PathVariable Long id) {
        return readingService.readByKunyomiId(id);
    }
}