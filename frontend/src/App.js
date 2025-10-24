import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import "./App.css";
import AdminApp from "./admin/AdminApp";
import UserApp from "./user/UserApp";
import Login from "./user/member/login/Login"; // 기존 로그인 컴포넌트 사용

function App() {
  // 토큰 존재 여부 확인 (기존 로그인 시스템과 호환)
  const token = localStorage.getItem("accessToken");
  const userRole = localStorage.getItem("role"); // 기존 시스템에서는 "role" 키 사용

  // 디버깅을 위한 콘솔 출력
  console.log("App.js - 현재 토큰:", token);
  console.log("App.js - 현재 역할:", userRole);

  return (
    <Router>
      <div className="App">
        <Routes>
          {/* 관리자 페이지 - 인증 필요 */}
          <Route
            path="/admin/*"
            element={
              token && userRole === "ADMIN" ? (
                <AdminApp />
              ) : (
                <Navigate to="/" replace /> // 로그인 페이지로 리다이렉트
              )
            }
          />

          {/* 사용자 페이지 - 인증 필요 */}
          <Route
            path="/user/*"
            element={
              token && userRole === "USER" ? (
                <UserApp />
              ) : (
                <Navigate to="/" replace /> // 로그인 페이지로 리다이렉트
              )
            }
          />

          {/* 메인 페이지 - 로그인 화면 */}
          <Route path="/" element={<Login />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
