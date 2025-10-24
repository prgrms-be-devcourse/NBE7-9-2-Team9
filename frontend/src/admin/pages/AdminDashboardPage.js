import React from "react";
import { useNavigate } from "react-router-dom";
import "./AdminDashboardPage.css";

const AdminDashboardPage = () => {
  const navigate = useNavigate();

  const handleMemberManagement = () => {
    // 회원 관리 페이지로 이동
    navigate("/admin/members");
  };

  const handlePlaceManagement = () => {
    // 기존 카테고리 목록으로 이동
    navigate("/admin/places");
  };

  return (
    <div className="admin-dashboard-page">
      <header className="page-header">
        <button className="back-button" onClick={() => navigate("/")}>
          ← 뒤로가기
        </button>
        <div className="header-content">
          <h1>관리자 대시보드</h1>
          <p>관리할 영역을 선택하세요</p>
        </div>
      </header>

      <div className="dashboard-grid">
        <div className="dashboard-card" onClick={handleMemberManagement}>
          <div className="card-icon">👥</div>
          <h2>회원 관리</h2>
          <p>사용자 계정 및 권한을 관리합니다</p>
          <div className="card-status available">사용 가능</div>
        </div>

        <div className="dashboard-card" onClick={handlePlaceManagement}>
          <div className="card-icon">🏞️</div>
          <h2>여행지 관리</h2>
          <p>여행지 카테고리 및 장소를 관리합니다</p>
          <div className="card-status available">사용 가능</div>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboardPage;
