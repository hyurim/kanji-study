package com.hyuri.kanji_study.domain.sentence.entity;

import com.hyuri.kanji_study.domain.kanji.entity.KanjiEntity;
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
@Table(name = "kun_sentence")
public class KunSentenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kanji_id", nullable = false)
    private KanjiEntity kanji;

    @Column(name = "kun_jp_text")   // 훈독 단어 문장
    private String kunJpText;

    @Column(name = "kun_kr_text")    // 문장 뜻
    private String kunKrText;
}
