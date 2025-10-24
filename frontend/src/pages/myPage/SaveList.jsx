import { useEffect, useMemo, useState } from "react";
import { fetchSavedKanjiList, removeSavedKanji } from "../../services/save";
import { fetchKanjiList } from "../../services/kanji";

const SavedList = () => {
  const [saved, setSaved] = useState([]);
  const [kanjiList, setKanjiList] = useState([]);
  const [loading, setLoading] = useState(true);
  const [err, setErr] = useState("");
  const [removingIds, setRemovingIds] = useState(new Set());

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      setErr("");
      try {
        const [savedRes, kanjiRes] = await Promise.all([
          fetchSavedKanjiList(),
          fetchKanjiList(),
        ]);
        setSaved(savedRes || []);
        setKanjiList(kanjiRes || []);
      } catch (e) {
        setErr(e?.message || "불러오기에 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  const mapById = useMemo(() => new Map(kanjiList.map(k => [k.id, k])), [kanjiList]);
  const savedKanjiData = useMemo(() => {
    return (saved || [])
      .map(s => {
        const k = mapById.get(s.kanjiId);
        return k ? { ...k, _savedAt: s.createdAt } : null;
      })
      .filter(Boolean)
	  .sort((a, b) => new Date(b._savedAt) - new Date(a._savedAt));
  }, [saved, mapById]);

  const onRemove = async (kanjiId) => {
    if (!kanjiId || removingIds.has(kanjiId)) return;
    if (!confirm("이 한자를 저장함에서 삭제할까요?")) return;

    const nextRemoving = new Set(removingIds);
    nextRemoving.add(kanjiId);
    setRemovingIds(nextRemoving);

    const prevSaved = saved;
    setSaved(prev => prev.filter(s => s.kanjiId !== kanjiId));

    try {
      await removeSavedKanji(kanjiId);
    } catch (e) {
      setSaved(prevSaved);
      alert(e?.message || "삭제에 실패했습니다.");
    } finally {
      const doneRemoving = new Set(nextRemoving);
      doneRemoving.delete(kanjiId);
      setRemovingIds(doneRemoving);
    }
  };

  if (loading) return <div>불러오는 중…</div>;

  return (
    <div style={{ padding: 16 }}>
      <h2>내가 저장한 한자</h2>

      {err && <div style={{ color: "crimson", marginBottom: 8 }}>{err}</div>}

      {savedKanjiData.length === 0 ? (
        <div>아직 저장한 한자가 없습니다.</div>
      ) : (
        <ul style={{ listStyle: "none", padding: 0, margin: 0 }}>
          {savedKanjiData.map(k => {
            const isRemoving = removingIds.has(k.id);
            return (
              <li
                key={k.id}
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
                <div style={{ fontSize: 32, lineHeight: 1 }}>{k.glyph}</div>
                <div style={{ fontSize: 16, flex: 1 }}>
                  <div>
                    {k.meaning} {k.reading ? `(${k.reading})` : ""}
                  </div>
                </div>
                <button
                  onClick={() => onRemove(k.id)}
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

export default SavedList;