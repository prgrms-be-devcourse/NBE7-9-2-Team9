import React from "react";
import { Routes, Route, Link } from "react-router-dom";
import ReviewListPage from "./ReviewListPage";
import ReviewFormPage from "./ReviewFormPage";
import "./ReviewApp.css";

const ReviewApp = () => {
  return (
    <div className="review-app">
      <h2>리뷰</h2>

      <Routes>
        <Route path="list" element={<ReviewListPage />} />
        <Route path="write" element={<ReviewFormPage />} />
        <Route path="edit" element={<ReviewFormPage isEdit={true} />} />
      </Routes>
    </div>
  );
};

export default ReviewApp;
