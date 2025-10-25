package com.hyuri.kanji_study.domain.saved.service;

import com.hyuri.kanji_study.domain.kanji.entity.KanjiEntity;
import com.hyuri.kanji_study.domain.saved.dto.SaveKanjiDto;
import com.hyuri.kanji_study.domain.saved.entity.SaveKanjiEntity;
import com.hyuri.kanji_study.domain.saved.repository.SaveKanjiRepository;
import com.hyuri.kanji_study.domain.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class SavedKanjiServiceImpl implements SavedKanjiService {

    private final SavedLookupSupport lookup;
    private final SaveKanjiRepository saveKanjiRepo;

    @Transactional
    public SaveKanjiDto addKanjiSave(String loginId, Long kanjiId) {
        UserEntity user = lookup.user(loginId);
        KanjiEntity kanji = lookup.kanji(kanjiId);

        if (saveKanjiRepo.existsByUserAndKanji(user, kanji)) {
            // 이미 저장되어 있으면 그대로 반환 (id는 기존 값 사용)
            SaveKanjiEntity exist = saveKanjiRepo.findByUser(user).stream()
                    .filter(se -> se
                            .getKanji().getId().equals(kanjiId)
                    )
                    .findFirst().orElseThrow();
            return new SaveKanjiDto(exist.getId(), user.getUserId(), kanji.getId());
        }

        SaveKanjiEntity saved = saveKanjiRepo.save(
                SaveKanjiEntity.builder()
                        .user(user)
                        .kanji(kanji)
                        .build()
        );
        return new SaveKanjiDto(saved.getId(), user.getUserId(), kanji.getId());
    }


    @Transactional(readOnly = true)
    public List<SaveKanjiDto> getSaveKanjiList(String loginId) {
        UserEntity user = lookup.user(loginId);
        return saveKanjiRepo.findByUser(user).stream()
                .map(se -> new SaveKanjiDto(se.getId(), user.getUserId(), se.getKanji().getId()))
                .toList();
    }

    @Transactional
    public void removeKanjiSave(String loginId, Long kanjiId) {
        UserEntity user = lookup.user(loginId);
        KanjiEntity kanji = lookup.kanji(kanjiId);
        if (saveKanjiRepo.deleteByUserAndKanji(user, kanji) == 0) {
            throw new IllegalArgumentException("저장 목록에 없는 한자입니다: " + kanjiId);
        }
    }
}
