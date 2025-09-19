package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.OnyomiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnyomiRepository extends JpaRepository<OnyomiEntity, Long> {
    List<OnyomiEntity> findByKanjiId(Long kanjiId);
}
