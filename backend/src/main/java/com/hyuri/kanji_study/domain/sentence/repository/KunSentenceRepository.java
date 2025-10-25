package com.hyuri.kanji_study.domain.sentence.repository;

import com.hyuri.kanji_study.domain.sentence.entity.KunSentenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KunSentenceRepository extends JpaRepository<KunSentenceEntity, Long> {
    List<KunSentenceEntity> findAllByKanji_Id(Long kanjiId);
}
