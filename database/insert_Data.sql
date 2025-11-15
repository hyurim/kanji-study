CREATE TABLE IF NOT EXISTS _staging_kunyomi (
  base_glyph  TEXT,      -- 기준 한자
  kun_glyph   TEXT,      -- 단어 한자 표기
  kun_kana    TEXT,      -- 히라가나
  kun_meaning TEXT       -- 뜻
);
SELECT * FROM _staging_kunyomi;
DROP TABLE _staging_kunyomi;

SELECT s.*
FROM _staging_kunyomi s
LEFT JOIN kanji k ON k.glyph = s.base_glyph
WHERE k.id IS NULL;

INSERT INTO kanji_kunyomi (kanji_id, kun_glyph, kun_kana, kun_meaning)
SELECT k.id, s.kun_glyph, s.kun_kana, s.kun_meaning
FROM _staging_kunyomi s
JOIN kanji k ON k.glyph = s.base_glyph;

----------------------

CREATE TABLE IF NOT EXISTS _staging_onyomi (
  base_glyph  TEXT,     -- 기준 한자
  on_glyph    TEXT,     -- 음독 한자 표기
  on_kana     TEXT,     -- 히라가나
  on_meaning  TEXT      -- 뜻
);

SELECT * FROM _staging_onyomi;
DROP TABLE _staging_onyomi;
-- 2) FK 매핑 실패 확인
SELECT s.*
FROM _staging_onyomi s
LEFT JOIN kanji k ON k.glyph = s.base_glyph
WHERE k.id IS NULL;

-- 3) 이관 (트림/NULL, 중복 무시)
INSERT INTO kanji_onyomi (kanji_id, on_glyph, on_kana, on_meaning)
SELECT
  k.id,
  NULLIF(btrim(s.on_glyph), '' )  AS on_glyph,
  NULLIF(btrim(s.on_kana),  '' )  AS on_kana,
  NULLIF(btrim(s.on_meaning), '') AS on_meaning
FROM _staging_onyomi s
JOIN kanji k ON k.glyph = s.base_glyph
WHERE NULLIF(btrim(s.on_glyph), '') IS NOT NULL;

-- 4) 검증
SELECT COUNT(*) AS inserted_onyomi
FROM kanji_onyomi oo
WHERE oo.id >= (SELECT COALESCE(min(id),1) FROM kanji_onyomi);



-----

-- 1) 스테이징
CREATE TABLE IF NOT EXISTS _staging_kun_sentence (
  base_glyph   TEXT,        -- 기준 한자
  kun_jp_text  TEXT,        -- 일본어 문장
  kun_kr_text  TEXT         -- 한국어 번역
);
SELECT * FROM _staging_kun_sentence;
DROP TABLE _staging_kun_sentence;

-- 2) FK 매핑 실패 확인
SELECT s.*
FROM _staging_kun_sentence s
LEFT JOIN kanji k ON k.glyph = s.base_glyph
WHERE k.id IS NULL;

-- 3) 이관
INSERT INTO kun_sentence (kanji_id, kun_jp_text, kun_kr_text)
SELECT
  k.id,
  NULLIF(btrim(s.kun_jp_text), '') AS kun_jp_text,
  NULLIF(btrim(s.kun_kr_text), '') AS kun_kr_text
FROM _staging_kun_sentence s
JOIN kanji k ON k.glyph = s.base_glyph
WHERE NULLIF(btrim(s.kun_jp_text), '') IS NOT NULL;

-- 4) 검증
SELECT COUNT(*) AS inserted_kun_sentence
FROM kun_sentence ks
WHERE ks.id >= (SELECT COALESCE(min(id),1) FROM kun_sentence);


-------


-- 1) 스테이징
CREATE TABLE IF NOT EXISTS _staging_on_sentence (
  base_glyph   TEXT,       -- 기준 한자
  on_jp_text   TEXT,       -- 일본어 문장
  on_kr_text   TEXT        -- 한국어 번역
);

SELECT * FROM _staging_on_sentence;
DROP TABLE _staging_on_sentence;

-- 2) FK 매핑 실패 확인
SELECT s.*
FROM _staging_on_sentence s
LEFT JOIN kanji k ON k.glyph = s.base_glyph
WHERE k.id IS NULL;

-- 3) 이관
INSERT INTO on_sentence (kanji_id, on_jp_text, on_kr_text)
SELECT
  k.id,
  NULLIF(btrim(s.on_jp_text), '') AS on_jp_text,
  NULLIF(btrim(s.on_kr_text), '') AS on_kr_text
FROM _staging_on_sentence s
JOIN kanji k ON k.glyph = s.base_glyph
WHERE NULLIF(btrim(s.on_jp_text), '') IS NOT NULL;

-- 4) 검증
SELECT COUNT(*) AS inserted_on_sentence
FROM on_sentence os
WHERE os.id >= (SELECT COALESCE(min(id),1) FROM on_sentence);
