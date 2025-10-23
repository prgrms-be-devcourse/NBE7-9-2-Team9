import React, { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import logo from "./logo.svg";
import "./App.css";
import AdminApp from "./admin/AdminApp";
import UserApp from "./user/UserApp";
import api from "./user/services/api";

function App() {
  const [recommendations, setRecommendations] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleRecommend = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await api.get("/api/review/recommend/1"); // 임시로 placeId 1 사용
      setRecommendations(response.data || []);
    } catch (err) {
      console.error("추천 여행지 조회 실패:", err);
      setError("추천 여행지를 불러오는데 실패했습니다.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <Router>
      <div className="App">
        <Routes>
          {/* 관리자 페이지 */}
          <Route path="/admin/*" element={<AdminApp />} />

          {/* 사용자 페이지 - 다른 팀원들이 작업할 공간 */}
          <Route path="/user/*" element={<UserApp />} />

          {/* 메인 페이지 - 팀원들이 협의하여 결정 */}
          <Route
            path="/"
            element={
              <div>
                <header className="App-header">
                  <img src={logo} className="App-logo" alt="logo" />
                  <h1>여행지 관리 시스템</h1>
                  <div
                    style={{ display: "flex", gap: "20px", marginTop: "20px" }}
                  >
                    <a className="App-link" href="/user">
                      사용자 페이지
                    </a>
                    <a className="App-link" href="/admin">
                      관리자 페이지
                    </a>
                    <button 
                      className="App-link" 
                      onClick={handleRecommend}
                      disabled={loading}
                      style={{ 
                        background: 'none', 
                        border: 'none', 
                        cursor: 'pointer',
                        opacity: loading ? 0.6 : 1
                      }}
                    >
                      {loading ? '추천 중...' : '여행지 추천'}
                    </button>
                  </div>
                  
                  {/* 추천 결과 표시 */}
                  {error && (
                    <div style={{ color: 'red', marginTop: '20px' }}>
                      {error}
                    </div>
                  )}
                  
                  {recommendations.length > 0 && (
                    <div style={{ marginTop: '30px', textAlign: 'left', maxWidth: '600px' }}>
                      <h3>추천 여행지</h3>
                      <div style={{ display: 'flex', flexDirection: 'column', gap: '10px' }}>
                        {recommendations.map((place) => (
                          <div key={place.id} style={{ 
                            border: '1px solid #ccc', 
                            padding: '10px', 
                            borderRadius: '5px',
                            backgroundColor: '#f9f9f9'
                          }}>
                            <div style={{ fontWeight: 'bold', fontSize: '16px' }}>
                              {place.placeName}
                            </div>
                            <div style={{ fontSize: '14px', color: '#666' }}>
                              {place.category} | 평점: {place.averageRating.toFixed(1)}
                            </div>
                            <div style={{ fontSize: '12px', color: '#888' }}>
                              {place.address} ({place.gu})
                            </div>
                            {place.description && (
                              <div style={{ fontSize: '12px', color: '#555', marginTop: '5px' }}>
                                {place.description}
                              </div>
                            )}
                          </div>
                        ))}
                      </div>
                    </div>
                  )}
                </header>
              </div>
            }
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
