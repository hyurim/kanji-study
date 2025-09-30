package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.KanjiEntity;
import com.hyuri.kanji_study.entity.SaveEntity;
import com.hyuri.kanji_study.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveRepository extends JpaRepository<SaveEntity, Long> {
    // 사용자 저장 목록
    List<SaveEntity> findByUser(UserEntity user);

    // 존재 여부 확인 (중복 저장 방지)
    boolean existsByUserAndKanji(UserEntity user, KanjiEntity kanji);

    // 삭제
    long deleteByUserAndKanji(UserEntity user, KanjiEntity kanji);
}
