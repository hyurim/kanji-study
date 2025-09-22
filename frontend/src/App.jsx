import { useEffect, useState } from "react";

export default function App() {
  const [list, setList] = useState([]);
  const [idx, setIdx] = useState(0);      // 현재 위치
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState(null);

  useEffect(() => {
    setLoading(true);
    fetch(`${import.meta.env.VITE_API_BASE}/api/kanji/list`)
      .then((r) => r.json())
      .then((data) => {
        setList(data || []);
        setIdx(0); // 처음은 0번
      })
      .catch((e) => setErr(e?.message || "load error"))
      .finally(() => setLoading(false));
  }, []);

  const hasData = list.length > 0;
  const cur = hasData ? list[idx] : null;

  const next = () => {
    if (!hasData) return;
    setIdx((prev) => (prev + 1) % list.length); // 마지막이면 처음으로
  };

  const prev = () => {
    if (!hasData) return;
    setIdx((prev) => (prev - 1 + list.length) % list.length); // 음수 방지
  };

  return (
    <div style={{ padding: 16, maxWidth: 480 }}>
      <h1>Kanji Viewer</h1>

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

          <div style={{ marginTop: 16, display: "flex", gap: 8 }}>
            <button onClick={prev}>이전</button>
            <button onClick={next}>다음</button>
            <span style={{ marginLeft: "auto" }}>
              {idx + 1} / {list.length}
            </span>
          </div>
        </div>
      )}
    </div>
  );
}