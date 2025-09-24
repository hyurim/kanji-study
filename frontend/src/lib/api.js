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
