-- 한자
INSERT INTO kanji (glyph, kunyomi, onyomi, meaning) VALUES
('学', 'まなぶ', 'がく, がっ', '배우다, 학문'),
('火', 'ひ', 'か', '불'),
('水', 'みず', 'すい', '물'),
('木', 'き', 'もく, ぼく', '나무'),
('山', 'やま', 'さん', '산');

select * from kanji;
UPDATE kanji
SET reading = '학1'
WHERE glyph = '学';
UPDATE kanji
SET reading = '화'
WHERE glyph = '火';
UPDATE kanji
SET reading = '수'
WHERE glyph = '水';
UPDATE kanji
SET reading = '목'
WHERE glyph = '木';
UPDATE kanji
SET reading = '산'
WHERE glyph = '山';
---------------------------------
-- 훈독 단어
INSERT INTO kanji_kunyomi (kanji_id, kun_glyph, kun_kana, kun_meaning)
VALUES
  ((SELECT id FROM kanji WHERE glyph='学'), '学ぶ', 'まなぶ', '배우다'),
  ((SELECT id FROM kanji WHERE glyph='火'), '火', 'ひ', '불'),
  ((SELECT id FROM kanji WHERE glyph='水'), '水', 'みず', '물'),
  ((SELECT id FROM kanji WHERE glyph='木'), '木', 'き', '나무'),
  ((SELECT id FROM kanji WHERE glyph='山'), '山', 'やま', '산');

---------------------------------

-- 음독 단어
INSERT INTO kanji_onyomi (kanji_id, on_glyph, on_kana, on_meaning)
VALUES
  ((SELECT id FROM kanji WHERE glyph='学'), '学校', 'がっこう', '학교'),
  ((SELECT id FROM kanji WHERE glyph='火'), '火山', 'かざん', '화산'),
  ((SELECT id FROM kanji WHERE glyph='水'), '水道', 'すいどう', '수도'),
  ((SELECT id FROM kanji WHERE glyph='木'), '木曜日', 'もくようび', '목요일'),
  ((SELECT id FROM kanji WHERE glyph='山'), '富士山', 'ふじさん', '후지산');
---------------------------------

-- 훈독 문장 
INSERT INTO kun_sentence (kanji_id, kun_jp_text, kun_kr_text)
VALUES
  ((SELECT id FROM kanji WHERE glyph='学'), '私は日本語を学ぶ。', '나는 일본어를 배운다.'),
  ((SELECT id FROM kanji WHERE glyph='火'), '火を消してください。', '불을 꺼주세요.'),
  ((SELECT id FROM kanji WHERE glyph='水'), '水を一杯ください。', '물 한 잔 주세요.'),
  ((SELECT id FROM kanji WHERE glyph='木'), '木の下で休みましょう。', '나무 아래에서 쉽시다.'),
  ((SELECT id FROM kanji WHERE glyph='山'), '山に登るのが好きです。', '산에 오르는 것을 좋아합니다.');

---------------------------------

-- 음독 문장
INSERT INTO on_sentence (kanji_id, on_jp_text, on_kr_text)
VALUES
  ((SELECT id FROM kanji WHERE glyph='学'), '彼は大学で日本文学を研究している。', '그는 대학에서 일본 문학을 연구하고 있다.'),
  ((SELECT id FROM kanji WHERE glyph='火'), '火山が噴火した。', '화산이 분화했다.'),
  ((SELECT id FROM kanji WHERE glyph='水'), '水曜日に会議があります。', '수요일에 회의가 있습니다.'),
  ((SELECT id FROM kanji WHERE glyph='木'), '木曜日は友達と映画を見る。', '목요일에는 친구와 영화를 본다.'),
  ((SELECT id FROM kanji WHERE glyph='山'), '富士山は日本で一番高い山です。', '후지산은 일본에서 가장 높은 산입니다.');

---------------------------------