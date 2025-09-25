import { Routes, Route, NavLink } from "react-router-dom";
import Home from "./pages/Home";
import Studylist from "./pages/StudyList";
import Savelist from "./pages/SaveList";
import Study from "./pages/Study";

const App = () => {
  return (
    <div style={{ maxWidth: 640, margin: "0 auto", padding: 16 }}>
      <header style={{ display: "flex", gap: 12, alignItems: "center", marginBottom: 16 }}>
        <h1 style={{ fontSize: 22, marginRight: "auto" }}>Kanji Study</h1>
        <NavLink to="/" end style={linkStyle}>
          홈
        </NavLink>
        <NavLink to="/studylist" style={linkStyle}>
          학습
        </NavLink>
        <NavLink to="/savelist" style={linkStyle}>
          저장함(미구현)
        </NavLink>
		<NavLink to="/study" style={linkStyle}>
          테스트
        </NavLink>
      </header>

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/studylist" element={<Studylist />} />
        <Route path="/savelist" element={<Savelist />} />
        <Route path="*" element={<NotFound />} />
		<Route path="/study" element={<Study />} />
      </Routes>

      <footer style={{ marginTop: 24, fontSize: 12, color: "#666" }}>
        © {new Date().getFullYear()} Kanji Study
      </footer>
    </div>
  );
}

export default App;

const linkStyle = ({ isActive }) => ({
	padding: "6px 10px",
	borderRadius: 8,
	textDecoration: "none",
	border: "1px solid #ddd",
	color: isActive ? "white" : "#333",
	background: isActive ? "#004488" : "transparent",
  });
  
const NotFound = () => {
	return <div>페이지를 찾을 수 없습니다.</div>;
  } 