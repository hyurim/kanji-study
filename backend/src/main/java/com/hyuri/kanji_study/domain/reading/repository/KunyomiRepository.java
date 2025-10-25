package com.hyuri.kanji_study.domain.reading.repository;

import com.hyuri.kanji_study.domain.reading.entity.KunyomiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KunyomiRepository extends JpaRepository<KunyomiEntity, Long> {
    List<KunyomiEntity> findAllByKanji_Id(Long kanjiId);
}
