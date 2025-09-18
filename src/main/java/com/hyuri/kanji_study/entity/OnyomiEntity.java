package com.hyuri.kanji_study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "kanji_onyomi")
public class OnyomiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kanji_id", nullable = false)
    private KanjiEntity kanji;

    @Column(name = "on_glyph")   // 음독 단어 한자 표기 (예: 学ぶ)
    private String onGlyph;

    @Column(name = "on_kana")    // 음독 단어 히라가나 (예: まなぶ)
    private String onKana;

    @Column(name = "on_meaning")  // 음독 단어 뜻
    private String onMeaning;
}
