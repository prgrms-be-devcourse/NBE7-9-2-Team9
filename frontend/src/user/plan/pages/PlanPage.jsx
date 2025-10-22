import React, { useState, useEffect } from "react";
import "./PlanPage.css";

export default function TravelPlanMain() {
  const [todayPlan, setTodayPlan] = useState(null);
  const [planDetails, setPlanDetails] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchTodayPlan();
  }, []);

  const fetchTodayPlan = async () => {
    try {
      setLoading(true);
      const planResponse = await fetch("http://localhost:8080/api/plan/today");
      if (!planResponse.ok) {
        if (planResponse.status === 404) {
          setTodayPlan(null);
          setPlanDetails([]);
          setLoading(false);
          return;
        }
        throw new Error("여행 계획을 불러오는데 실패했습니다.");
      }

      const planData = await planResponse.json();
      setTodayPlan(planData);

      const detailResponse = await fetch("http://localhost:8080/api/plan/detail/today");
      if (detailResponse.ok) {
        const detailData = await detailResponse.json();
        setPlanDetails(detailData);
      } else {
        setPlanDetails([]);
      }

      setLoading(false);
    } catch (err) {
      setError(err.message);
      setLoading(false);
    }
  };

  const formatDateTime = (dateTimeString) => {
    const date = new Date(dateTimeString);
    return date.toLocaleString("ko-KR", {
      month: "long",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  const formatTime = (dateTimeString) => {
    const date = new Date(dateTimeString);
    return date.toLocaleTimeString("ko-KR", {
      hour: "2-digit",
      minute: "2-digit",
    });
  };

  const handleCreatePlan = () => {
    window.location.href = "http://localhost:3000/user/plan/create";
  };

  const handleViewPlans = () => {
    window.location.href = "http://localhost:3000/user/plan/list";
  };

  return (
    <div className="main">
      <div className="main-container">
        <div className="header">
          <h1>나의 여행 계획</h1>
          <p>즐거운 여행을 계획하고 관리하세요</p>
        </div>

        <div className="button-group">
          <button className="primary" onClick={handleCreatePlan}>
            여행계획 작성하기
          </button>
          <button className="secondary" onClick={handleViewPlans}>
            여행계획 목록보기
          </button>
        </div>

        <div className="plan-card">
          <h2>📅 오늘의 여행 계획</h2>

          {loading ? (
            <div className="loading">
              <div className="spinner" />
              <p>로딩 중...</p>
            </div>
          ) : error ? (
            <div className="error">
              <p>{error}</p>
              <button onClick={fetchTodayPlan} className="retry">
                다시 시도
              </button>
            </div>
          ) : !todayPlan ? (
            <div className="empty">
              <div className="emoji">📅</div>
              <p className="main-text">오늘 예정된 여행 계획이 없습니다</p>
              <p className="sub-text">새로운 여행을 계획해보세요!</p>
            </div>
          ) : (
            <div>
              <div className="today-card">
                <h3>{todayPlan.title}</h3>
                {todayPlan.content && <p className="content">{todayPlan.content}</p>}
                <div className="date">
                  <span>🕐 {formatDateTime(todayPlan.startDate)}</span>
                  <span>~</span>
                  <span>{formatDateTime(todayPlan.endDate)}</span>
                </div>
              </div>

              {planDetails.length > 0 && (
                <div className="details">
                  <h4>세부 일정</h4>
                  {planDetails.map((detail) => (
                    <div key={detail.id} className="detail-card">
                      <div className="detail-header">
                        <h5>{detail.title}</h5>
                        <span className="time">
                          🕐 {formatTime(detail.startTime)} - {formatTime(detail.endTime)}
                        </span>
                      </div>
                      <div className="place">
                        📍 <span>{detail.placeName}</span>
                      </div>
                      {detail.content && <p className="detail-content">{detail.content}</p>}
                    </div>
                  ))}
                </div>
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
