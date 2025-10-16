import React from "react";
import { useParams, useNavigate, useLocation } from "react-router-dom";
import PlaceForm from "../components/organisms/PlaceForm";
import { createPlace, updatePlace } from "../services/placeService";
import "./PlaceFormPage.css";

const PlaceFormPage = () => {
  const { categoryId } = useParams();
  const navigate = useNavigate();
  const location = useLocation();

  // location.state에서 수정할 여행지 정보 가져오기
  const placeToEdit = location.state?.place;
  const isEditing = !!placeToEdit;

  // 카테고리 정보 (임시 데이터)
  const categoryMap = {
    1: { name: "관광지", icon: "🏛️" },
    2: { name: "맛집", icon: "🍽️" },
    3: { name: "숙소", icon: "🏨" },
  };

  const currentCategory = categoryMap[parseInt(categoryId)];

  const handleSubmit = async (formData) => {
    try {
      if (isEditing) {
        // 여행지 수정
        await updatePlace(placeToEdit.id, formData);
        alert("여행지가 성공적으로 수정되었습니다.");
      } else {
        // 여행지 등록
        await createPlace(formData);
        alert("여행지가 성공적으로 등록되었습니다.");
      }

      // 여행지 목록 페이지로 이동
      navigate(`/admin/places/${categoryId}`);
    } catch (error) {
      console.error("여행지 저장 실패:", error);
      alert(
        isEditing
          ? "여행지 수정에 실패했습니다."
          : "여행지 등록에 실패했습니다."
      );
    }
  };

  const handleCancel = () => {
    // 여행지 목록 페이지로 돌아가기
    navigate(`/admin/places/${categoryId}`);
  };

  if (!currentCategory) {
    return (
      <div className="place-form-page">
        <div className="error">존재하지 않는 카테고리입니다.</div>
      </div>
    );
  }

  return (
    <div className="place-form-page">
      <header className="page-header">
        <div className="header-left">
          <button className="back-button" onClick={handleCancel}>
            ← 여행지 목록으로
          </button>
          <div className="category-info">
            <span className="category-icon">{currentCategory.icon}</span>
            <h1>
              {currentCategory.name} {isEditing ? "수정" : "등록"}
            </h1>
          </div>
        </div>
      </header>

      <PlaceForm
        place={placeToEdit}
        categoryId={categoryId}
        onSubmit={handleSubmit}
        onCancel={handleCancel}
        isEditing={isEditing}
      />
    </div>
  );
};

export default PlaceFormPage;
