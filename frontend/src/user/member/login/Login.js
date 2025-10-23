// 📁 src/user/member/login/Login.js
import "../Member.css";
import React, { useState } from "react";
import { apiRequest } from "../../../utils/api";

const Login = () => {
  const [memberId, setMemberId] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

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

      localStorage.setItem("accessToken", data.data.accessToken);
      alert("✅ 로그인 성공!");
      window.location.href = "/user/member/mypage";
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

      <a href="/user/member/signup" className="member-link">
        회원가입하기
      </a>
    </div>
  );
};

export default Login;
