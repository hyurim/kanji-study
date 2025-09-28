package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.OnSentenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnSentenceRepository extends JpaRepository<OnSentenceEntity, Long> {
    List<OnSentenceEntity> findAllByKanji_Id(Long kanjiId);
}
