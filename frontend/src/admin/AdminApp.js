import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import CategoryListPage from "./pages/CategoryListPage";
import "./AdminApp.css";

const AdminApp = () => {
  return (
    <div className="admin-app">
      <Routes>
        <Route path="/" element={<Navigate to="/admin" replace />} />
        <Route path="/" element={<CategoryListPage />} />
        <Route
          path="/places/:categoryId"
          element={<div>여행지 목록 페이지 (추후 구현)</div>}
        />
      </Routes>
    </div>
  );
};

export default AdminApp;
