import { useEffect, useMemo, useState } from "react";
import { fetchSavedVocaList, removeSavedVoca } from "../../services/save";
import { fetchVocabList } from "../../services/Voca";

const SaveVocaList = () => {
  const [saved, setSaved] = useState([]);
  const [vocabList, setVocabList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState("");
  const [removingIds, setRemovingIds] = useState(new Set());

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      setErr("");
      try {
        const [savedRes, vocabRes] = await Promise.all([
          fetchSavedVocaList(),
          fetchVocabList(),
        ]);
        setSaved(savedRes || []);
        setVocabList(vocabRes || []);
      } catch (e) {
        setErr(e?.message || "불러오기에 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  const mapById = useMemo(
    () => new Map(vocabList.map(v => [(v.id ?? v.vocaId), v])),
    [vocabList]
  );

  const savedVocaData = useMemo(() => {
    return (saved || [])
      .map(s => {
        const v = mapById.get(s.vocabId);
        return v ? { ...v, _savedAt: s.createdAt, _linkId: s.vocabId } : null;
      })
      .filter(Boolean)
      .sort((a, b) => new Date(b._savedAt) - new Date(a._savedAt));
  }, [saved, mapById]);

  const onRemove = async (vocabId) => {
    if (!vocabId || removingIds.has(vocabId)) return;
    if (!confirm("이 단어를 저장함에서 삭제할까요?")) return;

    const nextRemoving = new Set(removingIds);
    nextRemoving.add(vocabId);
    setRemovingIds(nextRemoving);

    const prevSaved = saved;
    setSaved(prev => prev.filter(s => s.vocabId !== vocabId));

    try {
      await removeSavedVoca(vocabId);
    } catch (e) {
      setSaved(prevSaved);
      alert(e?.message || "삭제에 실패했습니다.");
    } finally {
      const doneRemoving = new Set(nextRemoving);
      doneRemoving.delete(vocabId);
      setRemovingIds(doneRemoving);
    }
  };

  if (loading) return <div>불러오는 중…</div>;

  return (
    <div style={{ padding: 16 }}>
      <h2>내가 저장한 단어</h2>

      {err && <div style={{ color: "crimson", marginBottom: 8 }}>{err}</div>}

      {savedVocaData.length === 0 ? (
        <div>아직 저장한 단어가 없습니다.</div>
      ) : (
        <ul style={{ listStyle: "none", padding: 0, margin: 0 }}>
          {savedVocaData.map(v => {
            const itemKey = v.id ?? v.vocaId;
            const isRemoving = removingIds.has(itemKey);

            const word = v.word ?? v.vocab ?? "-";
            const kana = v.kana ?? v.reading ?? "";
            const meaning = v.meaningKr ?? v.meaning ?? "";

            return (
              <li
                key={itemKey}
                style={{
                  border: "1px solid #ddd",
                  borderRadius: 8,
                  padding: 12,
                  marginBottom: 8,
                  display: "flex",
                  alignItems: "center",
                  gap: 12,
                }}
              >
                <div style={{ fontSize: 20, lineHeight: 1, minWidth: 80 }}>
                  {word}
                </div>
                <div style={{ fontSize: 16, flex: 1 }}>
                  <div>{meaning}</div>
                  {kana && <div style={{ color: "#555", marginTop: 4 }}>{kana}</div>}
                  {v.jlptLevel && (
                    <div style={{ fontSize: 12, color: "#666", marginTop: 2 }}>
                      {v.jlptLevel}
                    </div>
                  )}
                </div>
                <button
                  onClick={() => onRemove(itemKey)}
                  disabled={isRemoving}
                  style={{
                    padding: "6px 10px",
                    borderRadius: 8,
                    border: "1px solid #ccc",
                    background: isRemoving ? "#eee" : "#f5f5f5",
                    cursor: isRemoving ? "not-allowed" : "pointer",
                  }}
                  title="저장 취소"
                >
                  {isRemoving ? "삭제 중…" : "삭제"}
                </button>
              </li>
            );
          })}
        </ul>
      )}
    </div>
  );
};

export default SaveVocaList;