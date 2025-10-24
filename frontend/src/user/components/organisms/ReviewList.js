import React from "react";
import ReviewCard from "../molecules/ReviewCard";
import "./ReviewList.css";

const ReviewList = ({
  reviews,
  loading,
  error,
  onEdit,
  onDelete,
  canEdit = false,
}) => {
  if (loading) {
    return (
      <div className="review-list-loading">
        <div className="loading-spinner"></div>
        <p>리뷰를 불러오는 중...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="review-list-error">
        <p>❌ {error}</p>
      </div>
    );
  }

  if (!reviews || reviews.length === 0) {
    return (
      <div className="review-list-empty">
        <p>아직 작성된 리뷰가 없습니다.</p>
        <p>첫 번째 리뷰를 작성해보세요!</p>
      </div>
    );
  }

  return (
    <div className="review-list">
      {/* ✅ 아래쪽 리뷰 목록 텍스트 제거 */}
      {reviews.map((review) => (
        <ReviewCard
          key={review.reviewId}
          review={review}
          onEdit={onEdit}
          onDelete={onDelete}
          canEdit={canEdit}
        />
      ))}
    </div>
  );
};

export default ReviewList;
