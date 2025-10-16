import React from "react";
import Card from "../atoms/Card";
import "./CategoryCard.css";

const CategoryCard = ({ category, onClick, isSelected = false }) => {
  const handleClick = () => {
    if (onClick) {
      onClick(category);
    }
  };

  return (
    <Card
      className={`category-card ${isSelected ? "selected" : ""}`}
      onClick={handleClick}
      hoverable
    >
      <div className="category-icon">{category.icon || "🏛️"}</div>
      <h3 className="category-title">{category.name}</h3>
      <p className="category-description">{category.description}</p>
      <div className="category-count">{category.placeCount || 0}개 여행지</div>
    </Card>
  );
};

export default CategoryCard;
