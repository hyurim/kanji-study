import { useEffect, useState, useMemo } from "react";
import { fetchKanjiList } from "../lib/api";

const Study = () => {
  const [list, setList] = useState([]);
  const [idx, setIdx] = useState(0); // 현재 위치
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState(null);

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

  // 진행률
  const progress = useMemo(() => (hasData ? Math.round(((idx + 1) / list.length) * 100) : 0), [idx, hasData, list.length]);

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
          <div style={{ fontSize: 18, marginTop: 8 }}>{cur.meaning}</div>
          <div style={{ marginTop: 8 }}>훈독: {cur.kunyomi || "-"}</div>
          <div>음독: {cur.onyomi || "-"}</div>

          <div style={{ marginTop: 16, display: "flex", gap: 8, alignItems: "center" }}>
            <button onClick={prev}>이전</button>
            <button onClick={next}>다음</button>
            <span style={{ marginLeft: "auto" }}>
              {idx + 1} / {list.length} ({progress}%)
            </span>
          </div>
        </div>
      )}
    </div>
  );
}

export default Study;