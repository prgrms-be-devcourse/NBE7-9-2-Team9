import React from "react";
import "./StarRating.css";

const StarRating = ({ rating, onRatingChange, readOnly = false, size = "medium" }) => {
  const handleStarClick = (starRating) => {
    if (!readOnly && onRatingChange) {
      onRatingChange(starRating);
    }
  };

  return (
    <div className={`star-rating ${size}`}>
      {[1, 2, 3, 4, 5].map((star) => (
        <span
          key={star}
          className={`star ${star <= rating ? "filled" : "empty"}`}
          onClick={() => handleStarClick(star)}
          style={{ cursor: readOnly ? "default" : "pointer" }}
        >
          ★
        </span>
      ))}
    </div>
  );
};

export default StarRating;