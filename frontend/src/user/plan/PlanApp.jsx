// PlanApp.jsx
import { Routes, Route } from "react-router-dom";
import PlanListPage from "./pages/PlanListPage";
import PlanPage from "./pages/PlanPage";
import PlanCreatePage from "./pages/PlanCreatePage";

export default function PlanApp() {
  return (
    <Routes>
      <Route path="/" element={<PlanPage />} />
      <Route path="create" element={<PlanCreatePage />}/>
      <Route path="list" element={<PlanListPage />} />
      <Route path="detail/:id" element={<div>여행 상세 페이지</div>} />
    </Routes>
  );
}
