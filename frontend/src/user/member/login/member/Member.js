// 📁 src/user/member/login/member/Member.js
import React from "react";
import { useNavigate } from "react-router-dom";
import "../../Member.css";

const Member = () => {
  const navigate = useNavigate();

  const goToMyPage = () => {
    navigate("/user/member/login/member/mypage");
  };

  const goToLogout = () => {
    navigate("/user/member/login/member/logout"); // ✅ 로그아웃 페이지로 이동
  };

  return (
    <div className="member-container">
      <h2>회원 메인 페이지 🎉</h2>
      <p>로그인에 성공했습니다! 원하는 페이지로 이동할 수 있습니다.</p>

      <div
        style={{
          display: "flex",
          justifyContent: "center",
          gap: "1rem",
          marginTop: "2rem",
          flexWrap: "wrap",
        }}
      >
        <button onClick={goToMyPage} className="member-button">
          마이페이지로 이동
        </button>

        {/* ✅ 로그아웃 페이지로 이동 */}
        <button onClick={goToLogout} className="member-button danger">
          로그아웃
        </button>
      </div>
    </div>
  );
};

export default Member;
