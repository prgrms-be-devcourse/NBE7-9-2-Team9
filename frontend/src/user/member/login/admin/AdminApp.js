// 📁 src/user/member/login/admin/AdminApp.js
import React from "react";
import { Routes, Route, Link } from "react-router-dom";
import "../../Member.css";
import AdminMember from "./adminmember/AdminMember"; // 회원 관리 메인 페이지

// ✅ 관리자 홈
const AdminHome = () => (
  <div className="admin-container">
    <h1>관리자 페이지</h1>
    <p>관리자는 아래 기능을 통해 시스템을 관리할 수 있습니다.</p>

    <div
      style={{
        marginBottom: "2rem",
        display: "flex",
        gap: "1rem",
        flexWrap: "wrap",
        justifyContent: "center",
      }}
    >
      <Link to="/user/member/login/admin/place" className="admin-button">
        장소 관리
      </Link>
      <Link to="/user/member/login/admin/member" className="admin-button">
        회원 관리
      </Link>
    </div>
  </div>
);

// ✅ 장소 관리 페이지
const AdminPlace = () => (
  <div className="admin-container">
    <h2>장소 관리 페이지</h2>
    <p>등록된 장소를 조회, 수정, 삭제할 수 있습니다.</p>
  </div>
);

// ✅ 관리자 라우팅 구조 (여기가 핵심 수정 부분)
const AdminApp = () => {
  return (
    <div className="admin-app">
      <Routes>
        {/* 기본 홈 */}
        <Route path="/" element={<AdminHome />} />

        {/* 하위 페이지 */}
        <Route path="/place" element={<AdminPlace />} />
        <Route path="/member/*" element={<AdminMember />} /> {/* ✅ 여기 수정됨 */}
      </Routes>
    </div>
  );
};

export default AdminApp;
