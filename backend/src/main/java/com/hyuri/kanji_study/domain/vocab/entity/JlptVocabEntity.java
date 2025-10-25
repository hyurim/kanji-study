package com.hyuri.kanji_study.domain.vocab.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jlpt_vocab")
public class JlptVocabEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voca_id")
    private Long vocaId;

    @Column(name = "word", nullable = false, columnDefinition = "TEXT")
    private String word;

    @Column(name = "reading", columnDefinition = "TEXT")
    private String reading;

    @Column(name = "meaning_kr", columnDefinition = "TEXT")
    private String meaningKr;

    @Column(name = "jlpt_level", nullable = false, length = 5)
    private String jlptLevel; // N1~N5 (문자열로 관리)

    @Column(name = "part_of_speech", length = 50)
    private String partOfSpeech;

    @Column(name = "example_jp", columnDefinition = "TEXT")
    private String exampleJp;

    @Column(name = "example_kr", columnDefinition = "TEXT")
    private String exampleKr;
}
