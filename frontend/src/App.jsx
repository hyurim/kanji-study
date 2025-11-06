import { Routes, Route, NavLink } from "react-router-dom";
import { useAuth } from "./auth/useAuth";
import Home from "./pages/Home";
import Studylist from "./pages/StudyList";
import SaveSelect from "./pages/myPage/SaveSelect";
import SaveKanjiList from "./pages/myPage/SaveKanjiList";
import SaveVocaList from "./pages/myPage/SaveVocaList";
import Study from "./pages/Study";
import PageTest from "./pages/PageTest";
import Login from "./pages/User/Login";
import Signup from "./pages/User/Signup";
import Logout from "./pages/User/Logout";
import Vocab from "./pages/VocaTest";

const App = () => {
  const { user } = useAuth();

  return (
    <div style={{ maxWidth: 640, margin: "0 auto", padding: 16 }}>
      <header style={{ display: "flex", gap: 12, alignItems: "center", marginBottom: 16 }}>
        <h1 style={{ fontSize: 22, marginRight: "auto" }}>Kanji Study</h1>

        <NavLink to="/" end style={linkStyle}>홈</NavLink>
        <NavLink to="/studylist" style={linkStyle}>학습</NavLink>
        <NavLink to="/saveSelect" style={linkStyle}>저장함</NavLink>
        <NavLink to="/study" style={linkStyle}>한자 테스트</NavLink>
        <NavLink to="/vocab" style={linkStyle}>JLPT 테스트</NavLink>
        <NavLink to="/pagetest" style={linkStyle}>단어 출력 테스트</NavLink>

        {!user ? (
          <>
            <NavLink to="/login" style={linkStyle}>로그인</NavLink>
            <NavLink to="/signup" style={linkStyle}>회원가입</NavLink>
          </>
        ) : (
          <>
            <span style={{ fontSize: 12, color: "#666" }}>
              {user?.nickname} 님
            </span>
            <NavLink to="/logout" style={linkStyle}>로그아웃</NavLink>
          </>
        )}
      </header>
	  {/* 추후 로그인 비로그인 나눌 예정 */}

      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/studylist" element={<Studylist />} />
        <Route path="/saveSelect" element={<SaveSelect />} />
        <Route path="/study" element={<Study />} />
        <Route path="/vocab" element={<Vocab />} />
        <Route path="/pagetest" element={<PageTest />} />

        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/logout" element={<Logout />} />
        <Route path="/save/kanji" element={<SaveKanjiList />} />
        <Route path="/save/voca" element={<SaveVocaList />} />

        <Route path="*" element={<NotFound />} />
      </Routes>
			

      <footer style={{ marginTop: 24, fontSize: 12, color: "#666" }}>
        © {new Date().getFullYear()} Kanji Study
      </footer>
    </div>
  );
};

export default App;

const linkStyle = ({ isActive }) => ({
  padding: "6px 10px",
  borderRadius: 8,
  textDecoration: "none",
  border: "1px solid #ddd",
  color: isActive ? "white" : "#333",
  background: isActive ? "#004488" : "transparent",
});

const NotFound = () => <div>페이지를 찾을 수 없습니다.</div>;