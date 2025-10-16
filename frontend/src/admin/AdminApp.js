import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import CategoryListPage from "./pages/CategoryListPage";
import PlaceListPage from "./pages/PlaceListPage";
import "./AdminApp.css";

const AdminApp = () => {
  return (
    <div className="admin-app">
      <Routes>
        <Route path="/" element={<Navigate to="/admin" replace />} />
        <Route path="/" element={<CategoryListPage />} />
        <Route path="/places/:categoryId" element={<PlaceListPage />} />
      </Routes>
    </div>
  );
};

export default AdminApp;
