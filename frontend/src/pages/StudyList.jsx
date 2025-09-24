// src/pages/StudyRangeList.jsx
import { useNavigate } from "react-router-dom";

const groups = [
  {
    title: "1~200",
    ranges: ["1-50", "1-100", "1-150", "1-200"],
  },
  {
    title: "201~400",
    ranges: ["201-250", "201-300", "201-350", "201-400"],
  },
  {
    title: "401~600",
    ranges: ["401-450", "401-500", "401-550", "401-600"],
  },
  {
    title: "601~800",
    ranges: ["601-650", "601-700", "601-750", "601-800"],
  },
  {
    title: "801~1026",
    ranges: ["801-850", "801-900", "801-950", "801-1026"],
  },
  {
    title: "전체 범위",
    ranges: ["1-400", "401-800", "801-1026"],
  },
];

const StudyList = () => {
  const navigate = useNavigate();

  return (
    <div style={{ padding: 16 }}>
      <h2>학습 범위 선택</h2>
      {groups.map((group) => (
        <div
          key={group.title}
        >
          <h3 style={{ marginBottom: 12 }}>{group.title}</h3>
          <div>
            {group.ranges.map((range) => (
              <button
                key={range}
                onClick={() => navigate(`/studylist/${range}`)}
                >
                {range}
              </button>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};

export default StudyList;