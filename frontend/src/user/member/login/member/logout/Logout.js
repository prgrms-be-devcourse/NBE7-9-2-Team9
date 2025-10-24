import "../../../Member.css"; // ✅ 경로 수정됨 (3단계 위로)
import React from "react";
import { useNavigate } from "react-router-dom";
import { apiRequest } from "../../../../../utils/api"; // ✅ utils까지 5단계 경로 수정

const Logout = () => {
  const navigate = useNavigate();

  const handleLogout = async () => {
    try {
      const response = await apiRequest("http://localhost:8080/api/auth/logout", {
        method: "POST",
      });

      if (response.ok) {
        // ✅ 토큰 및 role 삭제
        localStorage.removeItem("accessToken");
        localStorage.removeItem("role");

        alert("🚪 로그아웃 완료!");
        navigate("/user/member/login"); // ✅ 로그인 페이지로 이동
      } else {
        alert("❌ 로그아웃 실패: 다시 시도해주세요.");
      }
    } catch (err) {
      console.error(err);
      alert("⚠️ 서버 오류로 로그아웃에 실패했습니다.");
    }
  };

  return (
    <div className="member-container">
      <h2>로그아웃</h2>

      <div className="member-form">
        <p>정말 로그아웃 하시겠습니까?</p>
        <button onClick={handleLogout} className="member-button danger">
          로그아웃
        </button>
      </div>

      {/* ✅ 마이페이지로 돌아가기 링크 */}
      <button
        type="button"
        onClick={() => navigate("/user/member/login/member/mypage")}
        className="member-button secondary"
        style={{ marginTop: "1rem" }}
      >
        마이페이지로 돌아가기
      </button>

      {/* ✅ 회원 홈으로 돌아가기 버튼 */}
      <button
        type="button"
        onClick={() => navigate("/user/member")}
        className="member-button secondary"
        style={{ marginTop: "0.5rem" }}
      >
        ← 회원 홈으로 돌아가기
      </button>
    </div>
  );
};

export default Logout;
