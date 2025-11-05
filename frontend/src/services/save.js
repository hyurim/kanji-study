import api from "../api/axios";

export async function fetchSavedKanjiList() {
  const { data } = await api.get("/save");
  return data || [];
}

export async function saveKanji(kanjiId) {
  const { data } = await api.post(`/save/${kanjiId}`);
  return data;
}

export async function removeSavedKanji(kanjiId) {
  await api.delete(`/save/${kanjiId}`);
}

export async function fetchSavedVocaList() {
  const { data } = await api.get("/save/vocab");
  return data || [];
}

export async function saveVoca(vocabId) {
  const { data } = await api.post(`/save/vocab/${vocabId}`);
  return data;
}

export async function removeSavedVoca(vocabId) {
  await api.delete(`/save/vocab/${vocabId}`);
}
