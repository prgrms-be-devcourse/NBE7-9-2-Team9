// 📁 src/components/molecules/ReviewCard.js

import React from "react";
import "./ReviewCard.css";

const ReviewCard = ({ review, onEdit, onDelete, canEdit }) => {
  const { rating, placeName, category, address, createdAt } = review;

  return (
    <div className="review-card">
      <div className="review-header">
        <span className="place-name">{placeName}</span>
        <span className="rating">⭐ {rating}/5</span>
      </div>

      <div className="review-subinfo">
        <span className="category">{category}</span>
        <span className="address">{address}</span>
      </div>

      <div className="review-footer">
        <span className="date">
  {review.modify_date
    ? new Date(review.modify_date).toLocaleString("ko-KR", {
        year: "numeric",
        month: "2-digit",
        day: "2-digit",
        hour: "2-digit",
        minute: "2-digit",
      })
    : ""}
</span>

        {canEdit && (
          <div className="review-actions">
            <button className="edit-btn" onClick={() => onEdit(review)}>
              수정
            </button>
            <button className="delete-btn" onClick={() => onDelete(review)}>
              삭제
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default ReviewCard;
