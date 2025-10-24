import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import logo from "./logo.svg";
import "./App.css";
import AdminApp from "./admin/AdminApp";
import UserApp from "./user/UserApp";

function App() {
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
                  </div>
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
