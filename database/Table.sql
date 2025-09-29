CREATE TABLE kanji (
  id          BIGSERIAL PRIMARY KEY,	-- 한자 ID
  glyph       TEXT NOT NULL UNIQUE,     -- 한자 (예: 学)
  kunyomi	  TEXT,						-- 훈독
  onyomi	  TEXT,						-- 음독
  meaning     TEXT NOT NULL            	-- 뜻 
);
ALTER TABLE kanji
ADD COLUMN reading VARCHAR(100) NOT NULL;

---------------------------------
-- 훈독 단어
CREATE TABLE kanji_kunyomi (
  id          BIGSERIAL PRIMARY KEY,
  kanji_id    BIGINT NOT NULL REFERENCES kanji(id) ON DELETE CASCADE,
  kun_glyph   TEXT,                     -- 훈독 단어 한자 표기 
  kun_kana    TEXT,         			-- 훈독 단어 히라가나 
  kun_meaning	  TEXT 						-- 훈독 단어 뜻
);

---------------------------------

-- 음독 단어
CREATE TABLE kanji_onyomi (
  id          BIGSERIAL PRIMARY KEY,
  kanji_id    BIGINT NOT NULL REFERENCES kanji(id) ON DELETE CASCADE,
  on_glyph 	  TEXT,                     -- 음독 한자 표기 (예: 学ぶ, 学校)
  on_kana     TEXT,        				-- 음독 히라가나 (예: まなぶ, がく)
  on_meaning  TEXT 						-- 훈독 단어 뜻
);

---------------------------------

-- 훈독 문장 
CREATE TABLE kun_sentence (
  id            BIGSERIAL PRIMARY KEY,
  kanji_id    BIGINT NOT NULL REFERENCES kanji(id) ON DELETE CASCADE,
  kun_jp_text       TEXT,           		-- 일본어 원문
  kun_kr_text       TEXT                    -- 한국어 번역(선택)
);

---------------------------------

-- 음독 문장
CREATE TABLE on_sentence (
  id            BIGSERIAL PRIMARY KEY,
  kanji_id    BIGINT NOT NULL REFERENCES kanji(id) ON DELETE CASCADE,
  on_jp_text       TEXT,           		-- 일본어 원문
  on_kr_text       TEXT                    -- 한국어 번역(선택)
);

---------------------------------

select * from kanji;
select * from kanji_onyomi;
select * from kanji_kunyomi;
select * from on_sentence;
select * from kun_sentence;

---------------------------------

-- 유저 테이블
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY, 					-- 내부 식별용
    login_id VARCHAR(50), 							-- 로그인용 아이디
    password VARCHAR(255), 							-- 비밀번호
    nickname VARCHAR(50), 							-- 닉네임
    last_study_date TIMESTAMP, 						-- 마지막 로그인 날짜
    streak_days INT, 								-- 연속으로 로그인한 날짜
    role VARCHAR(20), 								-- 유저 구분 (ADMIN은 직접 추가)
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 	-- 생성 날짜
);
-- 유저가 저장한 한자 
CREATE TABLE user_saved_kanji (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(user_id),
    kanji_id INT REFERENCES kanji(kanji_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
