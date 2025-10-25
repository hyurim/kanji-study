package com.hyuri.kanji_study.domain.vocab.repository;

import com.hyuri.kanji_study.domain.vocab.entity.JlptVocabEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JlptRepository extends JpaRepository<JlptVocabEntity, Long> {

}
