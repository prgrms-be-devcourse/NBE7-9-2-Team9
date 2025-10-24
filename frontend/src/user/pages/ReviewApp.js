import { Routes, Route, Navigate, useNavigate } from "react-router-dom";
import ReviewListPage from "./ReviewListPage";
import ReviewFormPage from "./ReviewFormPage";

function ReviewApp() {
  const navigate = useNavigate();

  return (
    <div className="review-app">
      <button className="back-button" onClick={() => navigate("/user")}>
        ← 뒤로가기
      </button>
      <Routes>
        {/* ✅ 기본 /user/review 경로일 때 list로 리다이렉트 */}
        <Route path="/" element={<Navigate to="list" replace />} />

        <Route path="list" element={<ReviewListPage />} />
        <Route path="write" element={<ReviewFormPage />} />
        <Route path="edit" element={<ReviewFormPage isEdit={true} />} />
      </Routes>
    </div>
  );
}

export default ReviewApp;
