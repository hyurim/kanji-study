import { useEffect, useState } from "react";
import {  
  fetchKunyomiList, 
  fetchKunSentenceList, 
  fetchOnyomiList, 
  fetchOnSentenceList  
} from "../lib/api";

const PageTest = () => {
  const [kunyomi, setKunyomi] = useState([]);
  const [kunSent, setKunSent] = useState([]);
  const [onyomi, setOnyomi] = useState([]);
  const [onSent, setOnSent] = useState([]);
  const [err, setErr] = useState(null);

  useEffect(() => {
    fetchKunyomiList()
      .then(setKunyomi)
      .catch(e => setErr(e.message));
    fetchKunSentenceList()
      .then(setKunSent)
      .catch(e => setErr(e.message));
    fetchOnyomiList()
      .then(setOnyomi)
      .catch(e => setErr(e.message));
    fetchOnSentenceList()
      .then(setOnSent)
      .catch(e => setErr(e.message));
  }, []);

  return (
    <div style={{ padding: 20 }}>
      <h2>데이터 출력 테스트</h2>
      {err && <div style={{ color: "crimson" }}>에러: {err}</div>}

      <section>
        <h3>훈독 단어</h3>
        <ul>
          {kunyomi.map((k) => (
            <li key={k.id}>
              {k.kunGlyph} ({k.kunKana}) - {k.kunMeaning}
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h3>훈독 예문</h3>
        <ul>
          {kunSent.map((s) => (
            <li key={s.id}>
              {s.kunJpText} → {s.kunKrText}
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h3>음독 단어</h3>
        <ul>
          {onyomi.map((o) => (
            <li key={o.id}>
              {o.onGlyph} ({o.onKana}) - {o.onMeaning}
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h3>음독 예문</h3>
        <ul>
          {onSent.map((s) => (
            <li key={s.id}>
              {s.onJpText} → {s.onKrText}
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
};

export default PageTest;