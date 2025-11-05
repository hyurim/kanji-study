export const API_BASE = import.meta.env.VITE_API_BASE;

export const fetchVocabList = async () => {
  const res = await fetch(`${API_BASE}/api/vocab/list`);
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return await res.json();
};
