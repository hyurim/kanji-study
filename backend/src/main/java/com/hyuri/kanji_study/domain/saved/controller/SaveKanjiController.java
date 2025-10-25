package com.hyuri.kanji_study.domain.saved.controller;

import com.hyuri.kanji_study.domain.saved.dto.SaveKanjiDto;
import com.hyuri.kanji_study.domain.saved.service.SavedKanjiService;
import com.hyuri.kanji_study.domain.user.repository.UserRepository;
import com.hyuri.kanji_study.common.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class SaveKanjiController {

    private final SavedKanjiService savedKanjiService;
    private final UserRepository userRepo;

    private Long currentUserId(Authentication auth) {
        var user = userRepo.findByLoginId(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자 없음"));
        return user.getUserId();
    }

    // 저장(중복 저장시 그대로 기존값 반환)
    @PostMapping("/{kanjiId}")
    public ResponseEntity<SaveKanjiDto> saveKanji(@PathVariable Long kanjiId,
                                                  @AuthenticationPrincipal AuthenticatedUser user) {
        SaveKanjiDto dto = savedKanjiService.addKanjiSave(user.getUsername(), kanjiId);
        return ResponseEntity.created(URI.create("/api/saves/" + dto.getId())).body(dto);
    }
    // 내 저장 목록 조회
    @GetMapping
    public ResponseEntity<List<SaveKanjiDto>> list(@AuthenticationPrincipal AuthenticatedUser user) {
        return ResponseEntity.ok(savedKanjiService.getSaveKanjiList(user.getUsername()));
    }

    // 저장 취소
    @DeleteMapping("/{kanjiId}")
    public ResponseEntity<Void> remove(@PathVariable Long kanjiId,
                                       @AuthenticationPrincipal AuthenticatedUser user) {
        savedKanjiService.removeKanjiSave(user.getUsername(), kanjiId);
        return ResponseEntity.noContent().build();
    }
}