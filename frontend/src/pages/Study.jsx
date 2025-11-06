import { useEffect, useMemo, useState } from "react";
import {
  fetchKanjiList,
  fetchKunyomiList,
  fetchOnyomiList,
  fetchKunSentenceList,
  fetchOnSentenceList,
} from "../services/Kanji";
import { fetchSavedKanjiList } from "../services/Save";
import { saveKanji as apiSaveKanji } from "../services/Save";


const Study = () => {
  const [list, setList] = useState([]);
  const [idx, setIdx] = useState(0);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState(null);

  const [detailLoading, setDetailLoading] = useState(false);
  const [detailErr, setDetailErr] = useState(null);
  const [loaded, setLoaded] = useState(false);
  const [maps, setMaps] = useState({
    kunyomiByKanji: {},
    onyomiByKanji: {},
    kunSentByKanji: {}, 
    onSentByKanji: {},
  });
  const [saving, setSaving] = useState(false);
  const [savedSet, setSavedSet] = useState(() => new Set());

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

  useEffect(() => {
    const loadDetails = async () => {
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
    loadDetails();
  }, [loaded]);

  useEffect(() => {
	    fetchSavedKanjiList()
	      .then((items) => setSavedSet(new Set((items || []).map(it => it.kanjiId))))
	      .catch(() => {});
	  }, []);
	

  const curId = cur?.id;
  const isSaved = !!(curId && savedSet.has(curId));

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

  const onSave = async () => {
	if (!curId || saving) return;
    if (savedSet.has(curId)) {
      alert("이미 저장된 한자입니다.");
      return;
    }
	setSaving(true);
	try {
		await apiSaveKanji(curId);
		setSavedSet(prev => new Set(prev).add(curId));
		alert("저장되었습니다.");
		} catch (e) {
		alert(e?.message || "저장에 실패했습니다.");
		} finally {
		setSaving(false);
		}
	};

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
          <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
            <div style={{ fontSize: 48, lineHeight: 1.2 }}>{cur.glyph}</div>
            <button
              onClick={onSave}
              disabled={saving || isSaved}
              style={{
                marginLeft: "auto",
                padding: "6px 10px",
                borderRadius: 8,
                border: "1px solid #ccc",
                background: isSaved ? "#eef8ee" : (saving ? "#eee" : "#f5f5f5"),
                cursor: (saving || isSaved) ? "not-allowed" : "pointer",
              }}
            >
              {isSaved ? "저장됨" : (saving ? "저장 중…" : "저장")}
            </button>
          </div>
          <div style={{ fontSize: 18, marginTop: 8 }}>{cur.meaning} {cur.reading}</div>
          <div style={{ marginTop: 8 }}>{cur.kunyomi || "-"} / {cur.onyomi || "-"}</div>

          <div style={{ 
            marginTop: 16, 
			      display: "flex", 
			      gap: 8, 
			      alignItems: "center", 
			      flexWrap: "wrap" 
			      }}
		      >
            <button onClick={prev}>이전</button>
            <button onClick={next}>다음</button>

            <span style={{ marginLeft: "auto" }}>
              {idx + 1} / {list.length} ({progress}%)
            </span>
          </div>

          {detailLoading && (
            <div style={{ marginTop: 8 }}>세부 정보 불러오는 중…</div>
          )}
          {detailErr && (
            <div style={{ marginTop: 8, color: "crimson" }}>{detailErr}</div>
          )}
          
          <div style={{ marginTop: 12 }}>
            <h4 style={{ margin: "8px 0" }}>단어</h4>
            {words.length === 0 ? (
              <div style={{ color: "#666" }}>단어 데이터가 없습니다.</div>
            ) : (
              <ul style={{ listStyle: "none", paddingLeft: 0, margin: 0 }}>
                {words.map((w) => {
                  const isKun = w._type === "kun";
                  const glyph = w.kunGlyph ?? w.onGlyph;
                  const kana = w.kunKana ?? w.onKana;
                  const mean = w.kunMeaning ?? w.onMeaning;
                  return (
                    <li key={`${w._type}-${w.id}`} style={{ marginBottom: 4 }}>
                      <span style={{ fontSize: 18, marginRight: 8 }}>{glyph}</span>
                      <span style={{ marginRight: 8 }}>{kana}</span>
                      <span>{mean}</span>
                      <span style={{ fontSize: 12 }}>{isKun ? " (훈)" : " (음)"}</span>
                    </li>
                  );
                })}
              </ul>
            )}
          </div>

          <div style={{ marginTop: 12 }}>
            <h4 style={{ margin: "8px 0" }}>예문</h4>
            {sentences.length === 0 ? (
              <div style={{ color: "#666" }}>예문 데이터가 없습니다.</div>
            ) : (
              <ul style={{ listStyle: "none", paddingLeft: 0, margin: 0 }}>
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
        </div>
      )}
    </div>
  );
};

export default Study;