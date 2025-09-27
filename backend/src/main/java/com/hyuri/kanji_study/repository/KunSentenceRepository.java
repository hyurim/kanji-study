package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.KunSentenceEntity;
import com.hyuri.kanji_study.entity.OnyomiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KunSentenceRepository extends JpaRepository<OnyomiEntity, Long> {
    List<KunSentenceEntity> findByKunSenId(Long kanjiId);
}
