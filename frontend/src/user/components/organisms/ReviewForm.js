import React, { useState, useEffect } from "react";
import Card from "../atoms/Card";
import Button from "../atoms/Button";
import StarRating from "../atoms/StarRating";
import { getCategories, getAllPlaces } from "../../services/placeService";
import "./ReviewForm.css";

const ReviewForm = ({
  placeId,
  placeName,
  onSubmit,
  onCancel,
  initialData = null,
  isEditing = false
}) => {
  const [formData, setFormData] = useState({
    memberId: initialData?.memberId || "",
    placeId: placeId || initialData?.placeId || "",
    rating: initialData?.rating || 0,
    Category: initialData?.Category || "",
    placeName: placeName || initialData?.placeName || "",
    address: initialData?.address || "",
    gu: initialData?.gu || ""
  });
  const [error, setError] = useState("");
  const [categories, setCategories] = useState([]);
  const [places, setPlaces] = useState([]);
  const [loading, setLoading] = useState(false);

  // 카테고리와 장소 목록 로드
  useEffect(() => {
    const loadData = async () => {
      try {
        setLoading(true);
        const [categoriesResponse, placesResponse] = await Promise.all([
          getCategories(),
          getAllPlaces()
        ]);
        setCategories(categoriesResponse.data || []);
        setPlaces(placesResponse.data || []);
      } catch (err) {
        console.error("데이터 로드 실패:", err);
        setError("카테고리와 장소 정보를 불러오는데 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };

    loadData();
  }, []);

  const handleRatingChange = (newRating) => {
    setFormData(prev => ({ ...prev, rating: newRating }));
    if (error) {
      setError("");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
    if (error) {
      setError("");
    }
  };

  // 장소 선택 시 해당 장소의 정보를 자동으로 채움
  const handlePlaceChange = (e) => {
    const selectedPlaceId = parseInt(e.target.value);
    const selectedPlace = places.find(place => place.id === selectedPlaceId);
    
    if (selectedPlace) {
      setFormData(prev => ({
        ...prev,
        placeId: selectedPlaceId,
        Category: selectedPlace.category || "",
        placeName: selectedPlace.placeName || "",
        address: selectedPlace.address || "",
        gu: selectedPlace.gu || ""
      }));
    } else {
      setFormData(prev => ({ ...prev, placeId: selectedPlaceId }));
    }
    
    if (error) {
      setError("");
    }
  };

  const validateForm = () => {
    if (formData.rating === 0) {
      setError("평점을 선택해주세요.");
      return false;
    }

    if (!formData.memberId.trim()) {
      setError("사용자 ID를 입력해주세요.");
      return false;
    }

    if (!formData.Category.trim()) {
      setError("카테고리를 입력해주세요.");
      return false;
    }

    if (!formData.placeName.trim()) {
      setError("여행지 이름을 입력해주세요.");
      return false;
    }

    if (!formData.address.trim()) {
      setError("주소를 입력해주세요.");
      return false;
    }

    if (!formData.gu.trim()) {
      setError("구를 입력해주세요.");
      return false;
    }

    return true;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (validateForm()) {
      const submitData = {
        memberId: parseInt(formData.memberId),
        placeId: parseInt(formData.placeId),
        rating: formData.rating,
        Category: formData.Category,
        placeName: formData.placeName,
        address: formData.address,
        gu: formData.gu
      };
      onSubmit(submitData);
    }
  };

  return (
    <Card className="review-form-container">
      <div className="review-form-header">
        <h3>{isEditing ? "리뷰 수정" : "리뷰 작성"}</h3>
        {placeName && (
          <p className="place-name">{placeName}</p>
        )}
      </div>

      <form onSubmit={handleSubmit} className="review-form">
        <div className="form-group">
          <label className="form-label">사용자 ID</label>
          <input
            type="number"
            name="memberId"
            value={formData.memberId}
            onChange={handleChange}
            placeholder="사용자 ID를 입력하세요"
            className="form-input"
            disabled={isEditing} // 수정 시에는 사용자 ID 변경 불가
          />
        </div>

        <div className="form-group">
          <label className="form-label">장소 선택</label>
          <select
            name="placeId"
            value={formData.placeId}
            onChange={handlePlaceChange}
            className="form-input"
            disabled={isEditing} // 수정 시에는 장소 변경 불가
          >
            <option value="">장소를 선택하세요</option>
            {places.map((place) => (
              <option key={place.id} value={place.id}>
                {place.placeName} ({place.category}) - {place.address}
              </option>
            ))}
          </select>
        </div>

        <div className="form-group">
          <label className="form-label">카테고리</label>
          <input
            type="text"
            name="Category"
            value={formData.Category}
            onChange={handleChange}
            placeholder="카테고리 (자동 입력됨)"
            className="form-input"
            readOnly
          />
        </div>

        <div className="form-group">
          <label className="form-label">여행지 이름</label>
          <input
            type="text"
            name="placeName"
            value={formData.placeName}
            onChange={handleChange}
            placeholder="여행지 이름 (자동 입력됨)"
            className="form-input"
            readOnly
          />
        </div>

        <div className="form-group">
          <label className="form-label">주소</label>
          <input
            type="text"
            name="address"
            value={formData.address}
            onChange={handleChange}
            placeholder="주소 (자동 입력됨)"
            className="form-input"
            readOnly
          />
        </div>

        <div className="form-group">
          <label className="form-label">구</label>
          <input
            type="text"
            name="gu"
            value={formData.gu}
            onChange={handleChange}
            placeholder="구 (자동 입력됨)"
            className="form-input"
            readOnly
          />
        </div>

        <div className="form-group">
          <label className="form-label">평점</label>
          <div className="rating-container">
            <StarRating
              rating={formData.rating}
              onRatingChange={handleRatingChange}
              size="large"
            />
            <span className="rating-text">
              {formData.rating > 0 ? `${formData.rating}/5` : "평점을 선택해주세요"}
            </span>
          </div>
        </div>

        {error && <div className="error-message">{error}</div>}

        {loading && (
          <div style={{ textAlign: 'center', padding: '20px', color: '#666' }}>
            장소 정보를 불러오는 중...
          </div>
        )}

        <div className="form-actions">
          <Button type="button" variant="outline" onClick={onCancel}>
            취소
          </Button>
          <Button type="submit" variant="primary" disabled={loading}>
            {isEditing ? "수정하기" : "리뷰 작성"}
          </Button>
        </div>
      </form>
    </Card>
  );
};

export default ReviewForm;