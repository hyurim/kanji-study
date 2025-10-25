package com.hyuri.kanji_study.domain.reading.controller;

import com.hyuri.kanji_study.domain.reading.dto.OnyomiDto;
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
@RequestMapping("/api/onyomi")
public class OnyomiController {

    private final ReadingService readingService;

    @GetMapping("/list")
    public List<OnyomiDto> list() {
        return readingService.getOnyomiListAll();
    }

    @GetMapping("/{id}")
    public OnyomiDto getOunyomi(@PathVariable Long id) {
        return readingService.readByOnyomiId(id);
    }
}