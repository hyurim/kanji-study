package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.JlptVocabEntity;
import com.hyuri.kanji_study.entity.SaveVocabEntity;
import com.hyuri.kanji_study.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaveVocabRepository extends JpaRepository<SaveVocabEntity, Long> {
    boolean existsByUserAndVocab(UserEntity user, JlptVocabEntity vocab);
    Optional<SaveVocabEntity> findByUserAndVocab(UserEntity user, JlptVocabEntity vocab);
    List<SaveVocabEntity> findByUser(UserEntity user);

    long deleteByUserAndVocab(UserEntity user, JlptVocabEntity vocab);

}
