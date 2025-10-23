import React from "react";
import { Routes, Route } from "react-router-dom";
import AdminDashboardPage from "./pages/AdminDashboardPage";
import CategoryListPage from "./pages/CategoryListPage";
import PlaceListPage from "./pages/PlaceListPage";
import PlaceFormPage from "./pages/PlaceFormPage";
import "./AdminApp.css";

const AdminApp = () => {
  return (
    <div className="admin-app">
      <Routes>
        <Route path="/" element={<AdminDashboardPage />} />
        <Route path="/places" element={<CategoryListPage />} />
        <Route path="/places/:categoryId" element={<PlaceListPage />} />
        <Route path="/places/:categoryId/new" element={<PlaceFormPage />} />
        <Route path="/places/:categoryId/edit" element={<PlaceFormPage />} />
      </Routes>
    </div>
  );
};

export default AdminApp;
