// 📁 src/user/member/signup/Signup.js
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiRequest } from "../../../utils/api";
import "../Member.css";

const Signup = () => {
  const [formData, setFormData] = useState({
    memberId: "",
    password: "",
    email: "",
    nickname: "",
  });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    try {
      const response = await apiRequest(
        "http://localhost:8080/api/members/signup",
        {
          method: "POST",
          body: JSON.stringify(formData),
        }
      );

      const data = await response.json();
      if (!response.ok) throw new Error(data.error?.message || "회원가입 실패");

      setMessage("✅ 회원가입 완료! 로그인 페이지로 이동합니다.");
      setTimeout(() => navigate("/user/member/login"), 1500);
    } catch (err) {
      console.error(err);
      setMessage("❌ 회원가입 실패: 입력 정보를 확인해주세요.");
    }
  };

  return (
    <div className="member-container">
      <h2>회원가입</h2>

      <form className="member-form" onSubmit={handleSubmit}>
        <input
          type="text"
          name="memberId"
          placeholder="아이디"
          value={formData.memberId}
          onChange={handleChange}
          required
        />
        <input
          type="password"
          name="password"
          placeholder="비밀번호"
          value={formData.password}
          onChange={handleChange}
          required
        />
        <input
          type="email"
          name="email"
          placeholder="이메일"
          value={formData.email}
          onChange={handleChange}
          required
        />
        <input
          type="text"
          name="nickname"
          placeholder="닉네임"
          value={formData.nickname}
          onChange={handleChange}
          required
        />

        <button type="submit" className="member-button">
          가입하기
        </button>

        {message && (
          <p
            className={message.startsWith("✅") ? "success-text" : "error-text"}
          >
            {message}
          </p>
        )}
      </form>

      {/* ✅ 로그인 페이지 이동 */}
      <a href="/user/member/login" className="member-link">
        이미 계정이 있으신가요? 로그인하기
      </a>

      {/* ✅ 회원 홈으로 돌아가기 버튼 */}
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

export default Signup;
