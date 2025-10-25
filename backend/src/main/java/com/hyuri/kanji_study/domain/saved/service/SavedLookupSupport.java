package com.hyuri.kanji_study.domain.saved.service;

import com.hyuri.kanji_study.domain.kanji.entity.KanjiEntity;
import com.hyuri.kanji_study.domain.kanji.repository.KanjiRepository;
import com.hyuri.kanji_study.domain.user.entity.UserEntity;
import com.hyuri.kanji_study.domain.user.repository.UserRepository;
import com.hyuri.kanji_study.domain.vocab.entity.JlptVocabEntity;
import com.hyuri.kanji_study.domain.vocab.repository.JlptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class SavedLookupSupport {
    private final KanjiRepository kanjiRepo;
    private final UserRepository userRepo;
    private final JlptRepository jlptRepo;

    UserEntity user(String loginId) {
        return userRepo.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다: " + loginId));
    }
    KanjiEntity kanji(Long kanjiId) {
        return kanjiRepo.findById(kanjiId)
                .orElseThrow(() -> new IllegalArgumentException("한자가 존재하지 않습니다: " + kanjiId));
    }
    JlptVocabEntity vocab(Long vocabId) {
        return jlptRepo.findById(vocabId)
                .orElseThrow(() -> new IllegalArgumentException("단어가 존재하지 않습니다: " + vocabId));
    }
}
