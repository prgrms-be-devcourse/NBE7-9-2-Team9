import React from "react";
import { Routes, Route } from "react-router-dom";
import ReviewListPage from "./pages/ReviewListPage";
import ReviewFormPage from "./pages/ReviewFormPage";
import "./UserApp.css";

const UserApp = () => {
  return (
    <div className="user-app">
      <Routes>
        <Route 
          path="/" 
          element={
            <div className="user-home">
              <h1>사용자 페이지</h1>
              <p>다른 팀원들이 여기서 사용자 기능을 개발할 수 있습니다.</p>
              <div className="feature-links">
                <a href="/user/plan">여행 계획</a>
                <a href="/user/review">리뷰</a>
                <a href="/user/bookmark">북마크</a>
                <a href="/user/member">회원</a>
              </div>
            </div>
          } 
        />
        <Route path="/plan/*" element={<div>여행 계획 페이지 (개발 예정)</div>} />
        <Route path="/review" element={<ReviewListPage />} />
        <Route path="/review/write" element={<ReviewFormPage />} />
        <Route path="/review/edit" element={<ReviewFormPage />} />
        <Route path="/bookmark/*" element={<div>북마크 페이지 (개발 예정)</div>} />
        <Route path="/member/*" element={<div>회원 페이지 (개발 예정)</div>} />
      </Routes>
    </div>
  );
};

export default UserApp;
