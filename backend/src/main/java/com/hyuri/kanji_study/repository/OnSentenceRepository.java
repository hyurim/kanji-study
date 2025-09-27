package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.OnSentenceEntity;
import com.hyuri.kanji_study.entity.OnyomiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnSentenceRepository extends JpaRepository<OnyomiEntity, Long> {
    List<OnSentenceEntity> findByOnSenId(Long kanjiId);
}
