// src/user/plan/PlanApp.jsx
import { Routes, Route, useNavigate } from "react-router-dom";
import PlanListPage from "./pages/PlanListPage";
import PlanPage from "./pages/PlanPage";
import PlanCreatePage from "./pages/PlanCreatePage";

export default function PlanApp() {
  const navigate = useNavigate();

  return (
    <div className="plan-app">
      <button className="back-button" onClick={() => navigate("/user")}>
        ← 뒤로가기
      </button>
      <Routes>
        {/* index = /user/plan */}
        <Route index element={<PlanPage />} />
        <Route path="create" element={<PlanCreatePage />} />
        <Route path="list" element={<PlanListPage />} />
      </Routes>
    </div>
  );
}
