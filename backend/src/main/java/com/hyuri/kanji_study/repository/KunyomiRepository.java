package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.KunyomiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KunyomiRepository extends JpaRepository<KunyomiEntity, Long> {
    List<KunyomiEntity> findByKanjiId(Long kanjiId);
}
