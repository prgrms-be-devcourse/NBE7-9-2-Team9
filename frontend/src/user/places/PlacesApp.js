import React from "react";
import { Routes, Route } from "react-router-dom";
import "./PlacesApp.css";
import CategoryListPage from "./pages/CategoryListPage";
import PlaceListPage from "./pages/PlaceListPage";

const PlacesApp = () => {
  return (
    <div className="places-app">
      <Routes>
        <Route path="/" element={<CategoryListPage />} />
        <Route path="/category/:categoryId" element={<PlaceListPage />} />
      </Routes>
    </div>
  );
};

export default PlacesApp;
