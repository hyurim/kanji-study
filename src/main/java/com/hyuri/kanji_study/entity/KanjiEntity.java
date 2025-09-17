package com.hyuri.kanji_study.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "kanji")
public class KanjiEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    /**
     * 실제 한자 글자
     */
    @Column(nullable = false, unique = true)
    private String glyph;
    /** 뜻 */
    @Column(nullable = false)
    private String meaning;

    /** 생성/수정 시각 (TIMESTAMPTZ) */
    @Column(name = "created_at", columnDefinition = "timestamptz")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamptz")
    private OffsetDateTime updatedAt;

    /* 편의 메서드 */
    public void addReading(KanjiReadingEntity r) {
        readings.add(r);
        r.setKanji(this);
    }

    public void removeReading(KanjiReadingEntity r) {
        readings.remove(r);
        r.setKanji(null);
    }

    /** DB의 DEFAULT now()를 쓰는 경우가 많아서, 애플리케이션에서도 null이면 채워줌 */
    @PrePersist
    public void prePersist() {
        var now = OffsetDateTime.now();
        if (createdAt == null) createdAt = now;
        if (updatedAt == null) updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
