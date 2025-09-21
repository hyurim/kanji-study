import { useEffect, useState } from "react";

export default function App() {
  const [list, setList] = useState([]);
  const [detail, setDetail] = useState(null);

  useEffect(() => {
    fetch(`${import.meta.env.VITE_API_BASE}/api/kanji/list`)
      .then((r) => r.json())
      .then(setList)
      .catch(console.error);

	  fetch(`${import.meta.env.VITE_API_BASE}/api/kanji/1`)
      .then((r) => r.json())
      .then(setDetail)
      .catch(console.error);
  }, []);

  return (
    <div style={{ padding: 16 }}>
      <h1>Kanji List</h1>
      {list.map((k) => (
        <div key={k.id} style={{ marginBottom: 12 }}>
          <b>{k.glyph}</b> — {k.meaning}
          <div>훈독: {k.kunyomi}</div>
          <div>음독: {k.onyomi}</div>
        </div>
      ))}

      <hr />
      <h2>Detail (id=1)</h2>
      {detail && <pre>{JSON.stringify(detail, null, 2)}</pre>}
    </div>
  );
}
