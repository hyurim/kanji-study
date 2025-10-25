package com.hyuri.kanji_study.domain.kanji.entity;

import com.hyuri.kanji_study.domain.reading.entity.KunyomiEntity;
import com.hyuri.kanji_study.domain.reading.entity.OnyomiEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "kanji")
public class KanjiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 실제 한자 글자 */
    @Column(nullable = false, unique = true)
    private String glyph;

    /** 훈독 */
    @Column(name = "kunyomi")
    private String kunyomi;

    /** 훈독 */
    @Column(name = "onyomi")
    private String onyomi;

    /** 뜻 */
    @Column(nullable = false)
    private String meaning;

    /** 음 */
    @Column(name = "reading", nullable = false)
    private String reading;

    @OneToMany(mappedBy = "kanji", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<KunyomiEntity> kunyomiWords = new ArrayList<>();

    @OneToMany(mappedBy = "kanji", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OnyomiEntity> onyomiWords = new ArrayList<>();
}
