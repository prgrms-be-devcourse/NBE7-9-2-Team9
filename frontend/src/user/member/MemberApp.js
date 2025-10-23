// 📁 src/user/member/MemberApp.js
import React from "react";
import { Routes, Route, Link } from "react-router-dom";
import Login from "./login/Login";
import Logout from "./logout/Logout";
import Signup from "./signup/Signup";
import MyPage from "./mypage/MyPage";
import "./Member.css";

const MemberApp = () => {
  return (
    <div className="member-container">
      <h2>회원 페이지</h2>
      <p>회원 관련 기능은 이곳에서 관리됩니다.</p>

      {/* ✅ 버튼 영역 */}
      <div style={{ marginBottom: "2rem", display: "flex", gap: "1rem", flexWrap: "wrap", justifyContent: "center" }}>
        <Link to="/user/member/login" className="member-button">로그인</Link>
        <Link to="/user/member/signup" className="member-button">회원가입</Link>
        <Link to="/user/member/logout" className="member-button">로그아웃</Link>
        <Link to="/user/member/mypage" className="member-button">마이페이지</Link>
      </div>

      {/* ✅ 하위 라우트 */}
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/logout" element={<Logout />} />
        <Route path="/mypage" element={<MyPage />} />
      </Routes>
    </div>
  );
};

export default MemberApp;
