package com.hyuri.kanji_study.controller;

import com.hyuri.kanji_study.dto.SaveDto;
import com.hyuri.kanji_study.security.AuthenticatedUser;
import com.hyuri.kanji_study.service.KanjiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/save")
public class SaveController {

    private final KanjiService kanjiService;

    // 저장(중복 저장시 그대로 기존값 반환)
    @PostMapping("/{kanjiId}")
    public ResponseEntity<SaveDto> saveKanji(@PathVariable Long kanjiId,
                                             @AuthenticationPrincipal AuthenticatedUser user) {
        SaveDto dto = kanjiService.addSave(user.getUsername(), kanjiId);
        return ResponseEntity.created(URI.create("/api/saves/" + dto.getId())).body(dto);
    }
    // 내 저장 목록 조회
    @GetMapping
    public ResponseEntity<List<SaveDto>> list(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(kanjiService.getSaveList(user.getUsername()));
    }

    // 저장 취소
    @DeleteMapping("/{kanjiId}")
    public ResponseEntity<Void> remove(@PathVariable Long kanjiId,
                                       @AuthenticationPrincipal AuthenticatedUser user) {
        kanjiService.removeSave(user.getUsername(), kanjiId);
        return ResponseEntity.noContent().build();
    }
}