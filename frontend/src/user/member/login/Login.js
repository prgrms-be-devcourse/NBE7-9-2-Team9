// ✅ [수정 완료] 로그인 성공 시 role에 따라 경로 분기 (기존 기능 그대로 유지)
import "../Member.css";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiRequest } from "../../../utils/api";

const Login = () => {
  const [memberId, setMemberId] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  // ✅ 로그인 요청 (AccessToken, Role 저장)
  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");

    try {
      const response = await apiRequest("http://localhost:8080/api/auth/login", {
        method: "POST",
        body: JSON.stringify({ memberId, password }),
      });

      const data = await response.json();

      if (!response.ok) throw new Error(data.error?.message || "로그인 실패");

      // ✅ 기존 로직 그대로 (토큰 + role 저장)
      localStorage.setItem("accessToken", data.data.accessToken);
      localStorage.setItem("role", data.data.role);

      alert("✅ 로그인 성공!");

      // ✅ 경로만 수정 (폴더 구조 반영)
      if (data.data.role === "ADMIN") {
        navigate("/user/member/login/admin"); // 관리자 페이지
      } else {
        navigate("/user/member/login/member"); // 일반 회원 페이지
      }
    } catch (err) {
      console.error(err);
      setError("로그인 실패: 아이디 또는 비밀번호를 확인하세요.");
    }
  };

  return (
    <div className="member-container">
      <h2>로그인</h2>

      <form className="member-form" onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="아이디"
          value={memberId}
          onChange={(e) => setMemberId(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit" className="member-button">
          로그인
        </button>

        {error && <p className="error-text">{error}</p>}
      </form>

      {/* 회원가입 링크 */}
      <a href="/user/member/signup" className="member-link">
        회원가입하기
      </a>

      {/* 홈으로 돌아가기 버튼 */}
      <button
        type="button"
        onClick={() => navigate("/user/member")}
        className="member-button secondary"
        style={{ marginTop: "1rem" }}
      >
        ← 회원 홈으로 돌아가기
      </button>
    </div>
  );
};

export default Login;
