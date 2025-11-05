import { useEffect, useMemo, useState } from "react";
import { fetchVocabList } from "../services/Voca";
import { fetchSavedVocaList, saveVoca as apiSaveVoca } from "../services/save";

const VocaTest = () => {
  const [list, setList] = useState([]);
  const [idx, setIdx] = useState(0);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState(null);

  const [saving, setSaving] = useState(false);
  const [savedSet, setSavedSet] = useState(() => new Set());

  useEffect(() => {
    setLoading(true);
    fetchVocabList()
      .then((data) => {
        setList(data || []);
        setIdx(0);
      })
      .catch((e) => setErr(e?.message || "load error"))
      .finally(() => setLoading(false));
  }, []);

  useEffect(() => {
    fetchSavedVocaList()
      .then((items) => setSavedSet(new Set((items || []).map((it) => it.vocabId))))
      .catch(() => {});
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

  const curId = cur?.id ?? cur?.vocaId;
  const isSaved = !!(curId && savedSet.has(curId));

  const onSave = async () => {
    if (!curId || saving) return;
    if (savedSet.has(curId)) {
      alert("이미 저장된 단어입니다.");
      return;
    }
    setSaving(true);
    try {
      await apiSaveVoca(curId);
      setSavedSet((prev) => new Set(prev).add(curId));
      alert("저장되었습니다.");
    } catch (e) {
      alert(e?.message || "저장에 실패했습니다.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div>
      <h2>어휘 모드</h2>

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
            <div style={{ fontSize: 36, lineHeight: 1.2 }}>
              {cur.word ?? cur.vocab ?? "-"}
							<p style={{ fontSize: 10 }}>
								{cur.jlptLevel ? <span style={{ marginLeft: 8 }}>{cur.jlptLevel}</span> : null}
							</p>
            </div>
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

          <div style={{ fontSize: 18, marginTop: 8 }}>
            {cur.meaningKr ? <span style={{ marginLeft: 8 }}>{cur.meaningKr}</span> : null}
						<br />
					</div>
					<div style={{ fontSize: 18, marginTop: 8 }}>
            {cur.reading ? <span>{cur.reading}</span> : null}
					</div>

          <div
            style={{
              marginTop: 16,
              display: "flex",
              gap: 8,
              alignItems: "center",
              flexWrap: "wrap",
            }}
          >
            <button onClick={prev}>이전</button>
            <button onClick={next}>다음</button>

            <span style={{ marginLeft: "auto" }}>
              {idx + 1} / {list.length} ({progress}%)
            </span>
          </div>
					<div style={{ marginTop: 12 }}>
						<h4 style={{ margin: "8px 0" }}>예문</h4>
						{(!cur.exampleJp && !cur.exampleKr) ? (
						<div style={{ color: "#666" }}>예문 데이터가 없습니다.</div>
						) : (
						<ul style={{ listStyle: "none", paddingLeft: 0, margin: 0 }}>
							{(!Array.isArray(cur.exampleJp) && !Array.isArray(cur.exampleKr)) && (
								<li style={{ marginBottom: 10 }}>
									<div style={{ fontSize: 16 }}>{cur.exampleJp || "-"}</div>
									{cur.exampleKr && <div style={{ color: "#333" }}>{cur.exampleKr}</div>}
								</li>
							)}
							
							{Array.isArray(cur.exampleJp) && cur.exampleJp.map((jp, i) => (
							<li key={`ex-${i}`} style={{ marginBottom: 10 }}>
								<div style={{ fontSize: 16 }}>{jp || "-"}</div>
								{Array.isArray(cur.exampleKr) && cur.exampleKr[i] && (
								<div style={{ color: "#333" }}>{cur.exampleKr[i]}</div>
							)}
							</li>
							))}
						</ul>
						)}
					</div>
        </div>
      )}
    </div>
		
  );
};

export default VocaTest;