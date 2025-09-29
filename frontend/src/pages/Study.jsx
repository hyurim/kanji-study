import { useEffect, useMemo, useState } from "react";
import {
  fetchKanjiList,
  fetchKunyomiList,
  fetchOnyomiList,
  fetchKunSentenceList,
  fetchOnSentenceList,
} from "../lib/api";

const Study = () => {
  const [list, setList] = useState([]);
  const [idx, setIdx] = useState(0);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState(null);

  const [openWords, setOpenWords] = useState(false);
  const [openSentences, setOpenSentences] = useState(false);

  const [detailLoading, setDetailLoading] = useState(false);
  const [detailErr, setDetailErr] = useState(null);
  const [loaded, setLoaded] = useState(false);
  const [maps, setMaps] = useState({
    kunyomiByKanji: {},
    onyomiByKanji: {},
    kunSentByKanji: {}, 
    onSentByKanji: {},
  });

  useEffect(() => {
    setLoading(true);
    fetchKanjiList()
      .then((data) => {
        setList(data || []);
        setIdx(0);
      })
      .catch((e) => setErr(e?.message || "load error"))
      .finally(() => setLoading(false));
  }, []);

  const hasData = list.length > 0;
  const cur = hasData ? list[idx] : null;

  useEffect(() => {
    setOpenWords(false);
    setOpenSentences(false);
    setDetailErr(null);
  }, [idx]);

  const next = () => {
    if (!hasData) return;
    setIdx((prev) => (prev + 1) % list.length);
  };

  const prev = () => {
    if (!hasData) return;
    setIdx((prev) => (prev - 1 + list.length) % list.length);
  };

  const progress = useMemo(
    () => (hasData ? Math.round(((idx + 1) / list.length) * 100) : 0),
    [idx, hasData, list.length]
  );

  const groupByKanji = (arr) => {
    const m = {};
    (arr || []).forEach((row) => {
      const kid = row?.kanji_id;
      if (kid == null) return;
      if (!m[kid]) m[kid] = [];
      m[kid].push(row);
    });
    return m;
  };

  const ensureDetails = async () => {
    if (loaded) return;
    setDetailLoading(true);
    setDetailErr(null);
    try {
      const [kunyomi, onyomi, kunSent, onSent] = await Promise.all([
        fetchKunyomiList().catch(() => []),
        fetchOnyomiList().catch(() => []),
        fetchKunSentenceList().catch(() => []),
        fetchOnSentenceList().catch(() => []),
      ]);

      setMaps({
        kunyomiByKanji: groupByKanji(kunyomi),
        onyomiByKanji: groupByKanji(onyomi),
        kunSentByKanji: groupByKanji(kunSent),
        onSentByKanji: groupByKanji(onSent),
      });
      setLoaded(true);
    } catch (e) {
      setDetailErr(e?.message || "세부 정보를 불러오지 못했습니다.");
    } finally {
      setDetailLoading(false);
    }
  };

  const onToggleWords = async () => {
    const willOpen = !openWords;
    setOpenWords(willOpen);
    if (willOpen) await ensureDetails();
  };

  const onToggleSentences = async () => {
    const willOpen = !openSentences;
    setOpenSentences(willOpen);
    if (willOpen) await ensureDetails();
  };

  const curId = cur?.id;

  const kunyomiList = (curId && maps.kunyomiByKanji[curId]) || [];
  const onyomiList  = (curId && maps.onyomiByKanji[curId])  || [];
  const kunSentences = (curId && maps.kunSentByKanji[curId]) || [];
  const onSentences  = (curId && maps.onSentByKanji[curId])  || [];

  const words = useMemo(() => ([
    ...kunyomiList.map(w => ({ ...w, _type: "kun" })),
    ...onyomiList.map(w => ({ ...w, _type: "on" })),
  ]), [kunyomiList, onyomiList]);

  const sentences = useMemo(() => ([
    ...kunSentences.map(s => ({ ...s, _type: "kun" })),
    ...onSentences.map(s => ({ ...s, _type: "on" })),
  ]), [kunSentences, onSentences]);

  return (
    <div>
      <h2>회독 모드</h2>

      {loading && <div>불러오는 중…</div>}
      {err && <div style={{ color: "crimson" }}>에러: {err}</div>}
      {!loading && !hasData && <div>데이터가 없습니다.</div>}

      {cur && (
        <div
          style={{
            border: "1px solid #ddd",
            borderRadius: 12,
            padding: 16,
            marginBottom: 12,
          }}
        >
          <div style={{ fontSize: 48, lineHeight: 1.2 }}>{cur.glyph}</div>
          <div style={{ fontSize: 18, marginTop: 8 }}>뜻 : {cur.meaning} 음 : {cur.reading}</div>
          <div style={{ marginTop: 8 }}>훈독: {cur.kunyomi || "-"}</div>
          <div>음독: {cur.onyomi || "-"}</div>

          <div style={{ marginTop: 16, display: "flex", gap: 8, alignItems: "center", flexWrap: "wrap" }}>
            <button onClick={prev}>이전</button>
            <button onClick={next}>다음</button>

            <button onClick={onToggleWords}>{openWords ? "단어 닫기" : "단어 보기"}</button>
            <button onClick={onToggleSentences}>{openSentences ? "문장 닫기" : "문장 보기"}</button>

            <span style={{ marginLeft: "auto" }}>
              {idx + 1} / {list.length} ({progress}%)
            </span>
          </div>

          {detailLoading && (openWords || openSentences) && (
            <div style={{ marginTop: 8 }}>세부 정보 불러오는 중…</div>
          )}
          {detailErr && (openWords || openSentences) && (
            <div style={{ marginTop: 8, color: "crimson" }}>{detailErr}</div>
          )}

          {openWords && (
            <div style={{ marginTop: 12 }}>
              <h4 style={{ margin: "8px 0" }}>단어</h4>
              {words.length === 0 ? (
                <div style={{ color: "#666" }}>단어 데이터가 없습니다.</div>
              ) : (
                <ul style={{ paddingLeft: 18, margin: 0 }}>
                  {words.map((w) => {
					const isKun = w._type === "kun";
                    const glyph = w.kunGlyph ?? w.onGlyph;
                    const kana  = w.kunKana ?? w.onKana;
                    const mean  = w.kunMeaning ?? w.onMeaning;
                    return (
                      <li key={`${w._type}-${w.id}`} style={{ marginBottom: 4 }}>
                        <span style={{ fontSize: 18, marginRight: 8 }}>{glyph}</span>
                        <span style={{ marginRight: 8 }}>{kana}</span>
                        <span >{mean}</span>
						<span style={{ fontSize: 12 }}>
							{isKun ? " (훈)" : " (음)"}
						</span>
                      </li>
                    );
                  })}
                </ul>
              )}
            </div>
          )}

          {openSentences && (
            <div style={{ marginTop: 12 }}>
              <h4 style={{ margin: "8px 0" }}>예문</h4>
              {sentences.length === 0 ? (
                <div style={{ color: "#666" }}>예문 데이터가 없습니다.</div>
              ) : (
                <ul style={{ paddingLeft: 18, margin: 0 }}>
                  {sentences.map((s) => {
                    const isKun = s._type === "kun";
                    const jp = isKun ? s.kunJpText : s.onJpText;
                    const ko = isKun ? s.kunKrText : s.onKrText;

                    return (
                      <li key={`${s._type}-${s.id}`} style={{ marginBottom: 10 }}>
                        <div style={{ fontSize: 16 }}>
                          {jp || "-"}
                          <span style={{ marginLeft: 8, fontSize: 12 }}>
                            {isKun ? " (훈)" : " (음)"}
                          </span>
                        </div>
                        {ko && <div style={{ color: "#333" }}>{ko}</div>}
                      </li>
                    );
                  })}
                </ul>
              )}
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default Study;