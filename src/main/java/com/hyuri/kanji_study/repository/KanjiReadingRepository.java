package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.KanjiReadingEntity;
import com.hyuri.kanji_study.entity.ReadingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KanjiReadingRepository extends JpaRepository<KanjiReadingEntity, Long> {
    /** 특정 한자 ID에 속한 모든 읽기 */
    List<KanjiReadingEntity> findByKanjiId(Long kanjiId);

    /** 특정 한자 ID + 타입(훈독/음독) */
    List<KanjiReadingEntity> findByKanjiIdAndType(Long kanjiId, ReadingType type);
}
