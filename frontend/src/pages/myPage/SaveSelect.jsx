// src/pages/myPage/SaveSelect.jsx
import { NavLink } from "react-router-dom";

const SaveSelect = () => {
  const baseBtn = {
    padding: "8px 12px",
    borderRadius: 8,
    border: "1px solid #ccc",
    background: "#f5f5f5",
    cursor: "pointer",
    textDecoration: "none",
    color: "black",
  };

  const activeBtn = {
    ...baseBtn,
    background: "#eef8ee",
    border: "1px solid #9ad09a",
    fontWeight: 600,
  };

  return (
    <div>
      <h2>저장 목록</h2>

      <div style={{ display: "flex", gap: 8, marginBottom: 12 }}>
        <NavLink
          to="/save/kanji"
          style={({ isActive }) => (isActive ? activeBtn : baseBtn)}
        >
          한자
        </NavLink>

        <NavLink
          to="/save/voca"
          style={({ isActive }) => (isActive ? activeBtn : baseBtn)}
        >
          단어
        </NavLink>
      </div>
    </div>
  );
};

export default SaveSelect;