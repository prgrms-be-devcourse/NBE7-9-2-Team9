// src/user/plan/PlanApp.jsx
import { Routes, Route } from "react-router-dom";
import PlanListPage from "./pages/PlanListPage";
import PlanPage from "./pages/PlanPage";
import PlanCreatePage from "./pages/PlanCreatePage";

export default function PlanApp() {
  return (
    <Routes>
      {/* index = /user/plan */}
      <Route index element={<PlanPage />} />
      <Route path="create" element={<PlanCreatePage />} />
      <Route path="list" element={<PlanListPage />} />
    </Routes>
  );
}
