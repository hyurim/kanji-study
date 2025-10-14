export const API_BASE = import.meta.env.VITE_API_BASE;

export async function fetchKanjiList() {
  const res = await fetch(`${API_BASE}/api/kanji/list`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

export async function fetchSavedKanji(userId) {
  const res = await fetch(`${API_BASE}/api/users/${userId}/saved`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

//kunyomi
export async function fetchKunyomiList() {
  const res = await fetch(`${API_BASE}/api/kunyomi/list`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

export async function fetchKunyomiById(id) {
  const res = await fetch(`${API_BASE}/api/kunyomi/${id}`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

//kunSentence
export async function fetchKunSentenceList() {
  const res = await fetch(`${API_BASE}/api/kunSentence/list`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

export async function fetchKunSentenceById(id) {
  const res = await fetch(`${API_BASE}/api/kunSentence/${id}`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}
//onyomi
export async function fetchOnyomiList() {
  const res = await fetch(`${API_BASE}/api/onyomi/list`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

export async function fetchOnyomiById(id) {
  const res = await fetch(`${API_BASE}/api/onyomi/${id}`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

//onSentence
export async function fetchOnSentenceList() {
  const res = await fetch(`${API_BASE}/api/onSentence/list`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

export async function fetchOnSentenceById(id) {
  const res = await fetch(`${API_BASE}/api/onSentence/${id}`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

export const fetchVocabList = async () => {
  const res = await fetch(`${import.meta.env.VITE_API_BASE}/api/vocab`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return await res.json();
};
