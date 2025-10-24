import React, { useState, useEffect } from "react";
import "../../../Member.css"; // ✅ CSS 경로 조정
import { apiRequest } from "../../../../../utils/api"; // ✅ API 유틸 연결

const AdminMember = () => {
  const [members, setMembers] = useState([]);
  const [selectedMember, setSelectedMember] = useState(null);
  const [error, setError] = useState("");

  /** ✅ 전체 회원 조회 */
  const fetchAllMembers = async () => {
    try {
      const response = await apiRequest("http://localhost:8080/api/admin/members", {
        method: "GET",
      });
      const data = await response.json();
      if (!response.ok) throw new Error(data.error?.message || "회원 목록 조회 실패");
      setMembers(data.data);
      setError("");
    } catch (err) {
      console.error(err);
      setError("회원 목록을 불러오는 중 오류가 발생했습니다.");
    }
  };

  /** ✅ 단건 조회 */
  const fetchMemberById = async (id) => {
    try {
      const response = await apiRequest(`http://localhost:8080/api/admin/members/${id}`, {
        method: "GET",
      });
      const data = await response.json();
      if (!response.ok) throw new Error(data.error?.message || "회원 조회 실패");
      setSelectedMember(data.data);
      setError("");
    } catch (err) {
      console.error(err);
      setError("회원 상세 조회 중 오류가 발생했습니다.");
    }
  };

  /** ✅ 회원 삭제 */
  const deleteMember = async (id) => {
    if (!window.confirm("정말 이 회원을 삭제하시겠습니까?")) return;
    try {
      const response = await apiRequest(`http://localhost:8080/api/admin/members/${id}`, {
        method: "DELETE",
      });
      if (!response.ok) throw new Error("회원 삭제 실패");
      alert("✅ 회원이 삭제되었습니다.");
      setSelectedMember(null);
      fetchAllMembers(); // 삭제 후 목록 새로고침
    } catch (err) {
      console.error(err);
      alert("❌ 회원 삭제 중 오류 발생");
    }
  };

  /** ✅ RefreshToken 무효화 */
  const invalidateToken = async (id) => {
    try {
      const response = await apiRequest(`http://localhost:8080/api/admin/members/${id}/token`, {
        method: "DELETE",
      });
      if (!response.ok) throw new Error("RefreshToken 무효화 실패");
      alert("🔒 RefreshToken이 무효화되었습니다.");
    } catch (err) {
      console.error(err);
      alert("❌ 토큰 무효화 중 오류 발생");
    }
  };

  useEffect(() => {
    fetchAllMembers();
  }, []);

  return (
    <div className="admin-container">
      <h2>회원 관리 페이지</h2>
      <p>전체 회원 조회, 단건 조회, 삭제 및 토큰 무효화 기능을 제공합니다.</p>

      <div style={{ marginTop: "2rem" }}>
        <button className="admin-button" onClick={fetchAllMembers}>
          전체 회원 조회
        </button>
      </div>

      {error && <p className="error-text">{error}</p>}

      {/* ✅ 전체 회원 테이블 */}
      {members.length > 0 && (
        <table className="admin-table" style={{ marginTop: "1.5rem", width: "100%" }}>
          <thead>
            <tr>
              <th>ID</th>
              <th>Member ID</th>
              <th>Email</th>
              <th>Nickname</th>
              <th>Role</th>
              <th>Status</th>
              <th>관리</th>
            </tr>
          </thead>
          <tbody>
            {members.map((member) => (
              <tr key={member.id}>
                <td>{member.id}</td>
                <td>{member.memberId}</td>
                <td>{member.email}</td>
                <td>{member.nickname}</td>
                <td>{member.role}</td>
                <td>{member.status}</td>
                <td>
                  <button
                    onClick={() => fetchMemberById(member.id)}
                    className="admin-small-btn"
                  >
                    조회
                  </button>
                  <button
                    onClick={() => deleteMember(member.id)}
                    className="admin-small-btn danger"
                  >
                    삭제
                  </button>
                  <button
                    onClick={() => invalidateToken(member.id)}
                    className="admin-small-btn secondary"
                  >
                    토큰무효화
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {/* ✅ 단건 회원 상세 정보 */}
      {selectedMember && (
        <div className="admin-detail-box" style={{ marginTop: "2rem" }}>
          <h3>회원 상세 정보</h3>
          <p><strong>ID:</strong> {selectedMember.id}</p>
          <p><strong>아이디:</strong> {selectedMember.memberId}</p>
          <p><strong>이메일:</strong> {selectedMember.email}</p>
          <p><strong>닉네임:</strong> {selectedMember.nickname}</p>
          <p><strong>권한:</strong> {selectedMember.role}</p>
          <button
            onClick={() => setSelectedMember(null)}
            className="admin-button secondary"
          >
            닫기
          </button>
        </div>
      )}
    </div>
  );
};

export default AdminMember;
