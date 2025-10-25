package com.hyuri.kanji_study.domain.reading.repository;

import com.hyuri.kanji_study.domain.reading.entity.OnyomiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnyomiRepository extends JpaRepository<OnyomiEntity, Long> {
    List<OnyomiEntity> findAllByKanji_Id(Long kanjiId);
}
