import React from "react";
import { Routes, Route } from "react-router-dom";
import "./UserApp.css";
import PlanApp from "./plan/PlanApp";
import MemberApp from "./member/MemberApp";
import BookmarkApp from "./bookmark/BookmarkApp";
import ReviewApp from "./pages/ReviewApp";

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
        <Route path="plan/*" element={<PlanApp />} />
        <Route path="review/*" element={<ReviewApp />} />
        <Route
          path="/bookmark/*"
          element={<BookmarkApp />}
        />
       <Route path="member/*" element={<MemberApp />} />
      </Routes>
    </div>
  );
};

export default UserApp;
