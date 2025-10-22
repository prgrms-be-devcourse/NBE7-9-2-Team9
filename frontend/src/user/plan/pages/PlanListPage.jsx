import React, { useState, useEffect } from "react";
import "./planListPage.css"; // 👈 여기에 아래 CSS 넣기

// 여행 계획 목록 페이지
function PlanListPage({ onSelectPlan }) {
  const [plans, setPlans] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchPlans();
  }, []);

  const fetchPlans = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/plan/list");
      if (!response.ok) throw new Error("계획 목록을 불러오는데 실패했습니다.");
      const result = await response.json();
      setPlans(result.data || []);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (dateTime) => {
    const date = new Date(dateTime);
    return date.toLocaleDateString("ko-KR", { year: "numeric", month: "long", day: "numeric" });
  };

  const truncate = (text, max = 30) => (text?.length > max ? text.substring(0, max) + "..." : text);

  if (loading) return <div className="center">로딩 중...</div>;
  if (error) return <div className="center error">{error}</div>;

  return (
    <div className="container">
      <button className="home-button" onClick={() => (window.location.href = "/user/plan")}>
        🏠 여행 홈
      </button>

      <h1>여행 계획 목록</h1>
      <p className="subtext">총 {plans.length}개의 여행 계획</p>

      {plans.length === 0 ? (
        <div className="empty">등록된 여행 계획이 없습니다.</div>
      ) : (
        <div className="grid">
          {plans.map((plan) => (
            <div key={plan.id} className="card" onClick={() => onSelectPlan(plan.id)}>
              <h3 className="title">{plan.title}</h3>
              <p className="content">{truncate(plan.content)}</p>
              <p className="date">
                {formatDate(plan.startDate)} ~ {formatDate(plan.endDate)}
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

// 여행 계획 상세 페이지
function PlanDetailPage({ planId, onBack }) {
  const [plan, setPlan] = useState(null);
  const [planDetails, setPlanDetails] = useState([]);
  const [isEditing, setIsEditing] = useState(false);
  const [editData, setEditData] = useState({
    title: "",
    content: "",
    startDate: "",
    endDate: "",
  });

  useEffect(() => {
    if (planId) fetchPlanDetail();
    fetchPlanDetailsList();
  }, [planId]);

  useEffect(() => {
    if (!isEditing && plan) {
      setEditData({
        title: plan.title,
        content: plan.content,
        startDate: plan.startDate,
        endDate: plan.endDate,
      });
    }
  }, [isEditing, plan]);

  const fetchPlanDetail = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/plan/${planId}`);
      if (!response.ok) throw new Error("계획 상세를 불러오는데 실패했습니다.");
      const result = await response.json();
      setPlan(result.data);
    } catch (err) {
      console.error(err);
    }
  };

  const fetchPlanDetailsList = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/plan/detail/${planId}/list`);
      if (!response.ok) throw new Error("상세 목록을 불러오는데 실패했습니다.");
      const result = await response.json();
      setPlanDetails(result.data || []);
    } catch (err) {
      console.error(err);
    }
  };

  const handleUpdate = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/plan/update/${planId}`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(editData),
      });
      if (!response.ok) throw new Error("수정 실패");
      const updated = await response.json();
      setPlan(updated);
      setIsEditing(false);
      alert("수정이 완료되었습니다!");
    } catch (err) {
      alert(err.message);
    }
  };

  if (!plan) return <div className="center">불러오는 중...</div>;

  return (
    <div className="container">
      <button className="back-button" onClick={onBack}>
        ← 목록으로
      </button>

      <div className="card detail">
        <h2>여행 계획 상세</h2>

        <label>제목</label>
        {isEditing ? (
          <input
            value={editData.title}
            onChange={(e) => setEditData({ ...editData, title: e.target.value })}
          />
        ) : (
          <p className="value">{plan.title}</p>
        )}

        <label>내용</label>
        {isEditing ? (
          <textarea
            value={editData.content}
            onChange={(e) => setEditData({ ...editData, content: e.target.value })}
            rows={5}
          />
        ) : (
          <p className="value">{plan.content}</p>
        )}

        <label>기간</label>
        {isEditing ? (
          <div className="date-inputs">
            <input
              type="datetime-local"
              value={editData.startDate}
              onChange={(e) => setEditData({ ...editData, startDate: e.target.value })}
            />
            <span>~</span>
            <input
              type="datetime-local"
              value={editData.endDate}
              onChange={(e) => setEditData({ ...editData, endDate: e.target.value })}
            />
          </div>
        ) : (
          <p className="value">
            {new Date(plan.startDate).toLocaleString()} ~{" "}
            {new Date(plan.endDate).toLocaleString()}
          </p>
        )}

        <div className="button-group">
          {!isEditing ? (
            <button className="primary" onClick={() => setIsEditing(true)}>
              수정
            </button>
          ) : (
            <>
              <button className="success" onClick={handleUpdate}>
                저장
              </button>
              <button className="gray" onClick={() => setIsEditing(false)}>
                취소
              </button>
            </>
          )}
        </div>
      </div>

      <h3>세부 일정</h3>
      {planDetails.length === 0 ? (
        <div className="empty">아직 상세 일정이 없습니다.</div>
      ) : (
        <div className="details">
          {planDetails.map((d) => (
            <div key={d.id} className="sub-card">
              <h4>{d.title}</h4>
              <p>📍 {d.placeName}</p>
              <p className="content">{d.content}</p>
              <p className="date">
                {new Date(d.startTime).toLocaleString()} ~ {new Date(d.endTime).toLocaleString()}
              </p>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

// 메인 앱
export default function App() {
  const [selectedPlanId, setSelectedPlanId] = useState(null);
  return selectedPlanId ? (
    <PlanDetailPage planId={selectedPlanId} onBack={() => setSelectedPlanId(null)} />
  ) : (
    <PlanListPage onSelectPlan={setSelectedPlanId} />
  );
}
