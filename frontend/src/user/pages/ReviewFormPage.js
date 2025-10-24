import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { createReview, modifyReview } from "../services/reviewService";
import { getAllPlaces } from "../services/placeService";
import "./ReviewFormPage.css";

const ReviewFormPage = ({ isEdit = false }) => {
  const navigate = useNavigate();
  const location = useLocation();
  const review = location.state?.review || null;

  const [rating, setRating] = useState(review?.rating || 0);
  const [placeId, setPlaceId] = useState(review?.placeId || "");
  const [category, setCategory] = useState(review?.category || "");
  const [address, setAddress] = useState(review?.address || "");
  const [places, setPlaces] = useState([]); // ✅ 여행지 목록
  const [hovered, setHovered] = useState(0);
  const [message, setMessage] = useState("");

  // ✅ 여행지 목록 불러오기
  useEffect(() => {
    const fetchPlaces = async () => {
      try {
        const response = await getAllPlaces(); // 이제 response는 배열
        setPlaces(Array.isArray(response) ? response : []); // ✅ 수정됨
        console.log("✅ 불러온 여행지 목록:", response);
      } catch (error) {
        console.error("여행지 목록 불러오기 실패:", error);
      }
    };
    fetchPlaces();
  }, []);

  // ✅ 수정 시 데이터 검증
  useEffect(() => {
    if (isEdit && !review) {
      setMessage("수정할 리뷰 데이터를 찾을 수 없습니다.");
    }
  }, [isEdit, review]);

  // ✅ 리뷰 등록 / 수정 핸들러
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (isEdit) {
        await modifyReview(review.reviewId, rating);
        alert("리뷰가 수정되었습니다.");
      } else {
        await createReview({ placeId, rating });
        alert("리뷰가 등록되었습니다.");
      }
      navigate("/user/review/list");
    } catch (err) {
      console.error("리뷰 저장 실패:", err);
      alert("리뷰 저장에 실패했습니다.");
    }
  };

  if (message) {
    return <div className="review-form-error">{message}</div>;
  }

  return (
    <div className="review-form-page">
      <h2>{isEdit ? "리뷰 수정" : "리뷰 작성"}</h2>

      <form onSubmit={handleSubmit}>
        {/* ✅ 여행지 선택 */}
        {!isEdit && (
          <div className="form-group">
            <label>여행지 선택</label>
            <select
              value={placeId}
              onChange={(e) => {
                const selected = places.find(
                  (p) => p.id === Number(e.target.value)
                );
                setPlaceId(e.target.value);
                setCategory(selected?.category || ""); // ✅ category 문자열 처리
                setAddress(selected?.address || "");
              }}
              required
            >
              <option value="">여행지를 선택하세요</option>
              {places.map((place) => (
                <option key={place.id} value={place.id}>
                  {place.placeName}
                  {place.category ? ` (${place.category})` : ""}
                </option>
              ))}
            </select>
          </div>
        )}

        {/* ✅ 카테고리 */}
        <div className="form-group">
          <label>카테고리</label>
          <input type="text" value={category} readOnly />
        </div>

        {/* ✅ 주소 */}
        <div className="form-group">
          <label>주소</label>
          <input type="text" value={address} readOnly />
        </div>

        {/* ✅ 별점 입력 */}
        <div className="form-group rating-group">
          <label>평점</label>
          <div className="star-rating">
            {[1, 2, 3, 4, 5].map((star) => (
              <span
                key={star}
                className={`star ${
                  (hovered || rating) >= star ? "filled" : ""
                }`}
                onClick={() => setRating(star)}
                onMouseEnter={() => setHovered(star)}
                onMouseLeave={() => setHovered(0)}
              >
                ★
              </span>
            ))}
            <span className="rating-text">{rating}/5</span>
          </div>
        </div>

        {/* ✅ 버튼 */}
        <div className="form-actions">
          <button
            type="button"
            className="cancel-btn"
            onClick={() => navigate("/user/review/list")}
          >
            취소
          </button>
          <button type="submit" className="submit-btn">
            {isEdit ? "수정하기" : "등록하기"}
          </button>
        </div>
      </form>
    </div>
  );
};

export default ReviewFormPage;
