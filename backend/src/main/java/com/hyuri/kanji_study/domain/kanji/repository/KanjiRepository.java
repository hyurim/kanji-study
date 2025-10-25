package com.hyuri.kanji_study.domain.kanji.repository;

import com.hyuri.kanji_study.domain.kanji.entity.KanjiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KanjiRepository extends JpaRepository<KanjiEntity, Long> {

}
