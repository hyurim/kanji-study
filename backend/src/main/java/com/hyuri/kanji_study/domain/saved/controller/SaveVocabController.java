package com.hyuri.kanji_study.domain.saved.controller;

import com.hyuri.kanji_study.domain.saved.dto.SaveVocabDto;
import com.hyuri.kanji_study.common.security.AuthenticatedUser;
import com.hyuri.kanji_study.domain.saved.service.SavedVocabService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/save/vocab")
public class SaveVocabController {

    private final SavedVocabService savedVocabService;

    /** 단어 저장 (중복 시 기존값 반환) */
    @PostMapping("/{vocabId}")
    public ResponseEntity<SaveVocabDto> saveVocab(@PathVariable Long vocabId,
                                                  @AuthenticationPrincipal AuthenticatedUser user) {
        SaveVocabDto dto = savedVocabService.saveVocab(user.getUsername(), vocabId);
        return ResponseEntity.created(URI.create("/api/save/vocab/" + dto.getId())).body(dto);
    }

    /** 내 단어 저장 목록 조회 */
    @GetMapping
    public ResponseEntity<List<SaveVocabDto>> list(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(savedVocabService.getSaveVocabList(user.getUsername()));
    }

    /** 단어 저장 취소 */
    @DeleteMapping("/{vocabId}")
    public ResponseEntity<Void> remove(@PathVariable Long vocabId,
                                       @AuthenticationPrincipal AuthenticatedUser user) {
        savedVocabService.removeVocabSave(user.getUsername(), vocabId);
        return ResponseEntity.noContent().build();
    }
}