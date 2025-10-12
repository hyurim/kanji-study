package com.hyuri.kanji_study.repository;

import com.hyuri.kanji_study.entity.JlptVocabEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JlptRepository extends JpaRepository<JlptVocabEntity, Long> {

}
