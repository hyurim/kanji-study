package com.hyuri.kanji_study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kanji_reading")
public class KanjiReadingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 한자 FK */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kanji_id", nullable = false)
    private KanjiEntity kanji;

    /** 훈독/음독 구분
     *  PostgreSQL enum(reading_type)에 매핑하기 위해 columnDefinition 지정
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "reading_type")
    private ReadingTypeEntity type;

    /** 읽기 한자 표기 (예: 学ぶ, 学校) */
    @Column(name = "kanji_form")
    private String kanjiForm;

    /** 히라가나 (예: まなぶ, がく) */
    @Column(nullable = false)
    private String kana;

    /** 생성 시각 (TIMESTAMPTZ) */
    @Column(name = "created_at", columnDefinition = "timestamptz")
    private OffsetDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
}
