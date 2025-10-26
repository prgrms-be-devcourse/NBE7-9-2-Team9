import React from "react";
import { Routes, Route } from "react-router-dom";
import "./UserApp.css";
import PlanApp from "./plan/PlanApp";
import PlacesApp from "./places/PlacesApp";
import MemberApp from "./member/MemberApp";
import BookmarkApp from "./bookmark/BookmarkApp";
import ReviewApp from "./pages/ReviewApp";
import LogoutButton from "../components/LogoutButton";

const UserApp = () => {
  return (
    <div className="user-app">
      <LogoutButton />
      <Routes>
        <Route
          path="/"
          element={
            <div className="user-home">
              <h1>갈래? 말래? 가자 서울! ✈️</h1>
              <p>서울 여행의 모든 것을 한 곳에서 만나보세요</p>
              <div className="feature-links">
                <a href="/user/plan">여행 계획</a>
                <a href="/user/places">여행지</a>
                <a href="/user/review">리뷰</a>
                <a href="/user/bookmark">북마크</a>
                <a href="/user/member/login/member/mypage">회원</a>
              </div>
            </div>
          }
        />
        <Route path="plan/*" element={<PlanApp />} />
        <Route path="places/*" element={<PlacesApp />} />
        <Route path="review/*" element={<ReviewApp />} />
        <Route path="bookmark/*" element={<BookmarkApp />} />
        <Route path="member/*" element={<MemberApp />} />
      </Routes>
    </div>
  );
};

export default UserApp;
