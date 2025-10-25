package com.hyuri.kanji_study.domain.saved.service;

import com.hyuri.kanji_study.domain.saved.dto.SaveVocabDto;
import com.hyuri.kanji_study.domain.saved.entity.SaveVocabEntity;
import com.hyuri.kanji_study.domain.saved.repository.SaveVocabRepository;
import com.hyuri.kanji_study.domain.user.entity.UserEntity;
import com.hyuri.kanji_study.domain.vocab.entity.JlptVocabEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class SavedVocabServiceImpl implements SavedVocabService {


    private final SaveVocabRepository saveVocabRepo;
    private final SavedLookupSupport lookup;

    @Transactional
    public SaveVocabDto saveVocab(String loginId, Long vocabId) {
        UserEntity user = lookup.user(loginId);
        JlptVocabEntity vocab = lookup.vocab(vocabId);

        if (saveVocabRepo.existsByUserAndVocab(user, vocab))
            throw new IllegalStateException("이미 저장된 단어입니다.");

        SaveVocabEntity saved = saveVocabRepo.save(
                SaveVocabEntity.builder()
                        .user(user)
                        .vocab(vocab)
                        .build()
        );

        return new SaveVocabDto(
                saved.getId(),
                user.getUserId(),
                vocab.getVocaId()
        );
    }

    @Transactional(readOnly = true)
    public List<SaveVocabDto> getSaveVocabList(String loginId) {
        UserEntity user = lookup.user(loginId);
        return saveVocabRepo.findByUser(user).stream()
                .map(se -> new SaveVocabDto(
                        se.getId(),
                        user.getUserId(),
                        se.getVocab().getVocaId()
                ))
                .toList();
    }

    @Transactional
    public void removeVocabSave(String loginId, Long vocabId) {
        UserEntity user = lookup.user(loginId);
        JlptVocabEntity vocab = lookup.vocab(vocabId);
        if (saveVocabRepo.deleteByUserAndVocab(user, vocab) == 0) {
            throw new IllegalArgumentException("저장 목록에 없는 단어입니다: " + vocabId);
        }
    }
}
