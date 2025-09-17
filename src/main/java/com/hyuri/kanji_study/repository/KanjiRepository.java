package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.KanjiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KanjiRepository extends JpaRepository<KanjiEntity, Long> {
    /** 한자 글자로 검색 */
    Optional<KanjiEntity> findByGlyph(String glyph);
}
