import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import PlaceList from "../components/organisms/PlaceList";
import Button from "../components/atoms/Button";
import { getPlacesByCategory } from "../services/categoryService";
import { deletePlace } from "../services/placeService";
import "./PlaceListPage.css";

const PlaceListPage = () => {
  const { categoryId } = useParams();
  const navigate = useNavigate();
  const [places, setPlaces] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // 카테고리 정보 (임시 데이터)
  const categoryMap = {
    1: { name: "관광지", icon: "🏛️" },
    2: { name: "맛집", icon: "🍽️" },
    3: { name: "숙소", icon: "🏨" },
  };

  const currentCategory = categoryMap[parseInt(categoryId)];

  useEffect(() => {
    fetchPlaces();
  }, [categoryId]);

  const fetchPlaces = async () => {
    try {
      setLoading(true);
      const response = await getPlacesByCategory(categoryId);

      // 백엔드 응답 데이터를 그대로 사용
      setPlaces(response.data || []);
      setError(null);
    } catch (err) {
      setError("여행지 목록을 불러오는데 실패했습니다.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleBackToCategories = () => {
    navigate("/admin");
  };

  const handleAddPlace = () => {
    navigate(`/admin/places/${categoryId}/new`);
  };

  const handleEditPlace = (place) => {
    navigate(`/admin/places/${categoryId}/edit`, {
      state: { place },
    });
  };

  const handleDeletePlace = async (place) => {
    if (window.confirm(`"${place.placeName}"을(를) 정말 삭제하시겠습니까?`)) {
      try {
        await deletePlace(place.id);
        alert("여행지가 삭제되었습니다.");
        // 목록 새로고침
        fetchPlaces();
      } catch (error) {
        console.error("여행지 삭제 실패:", error);
        alert("여행지 삭제에 실패했습니다.");
      }
    }
  };

  if (!currentCategory) {
    return (
      <div className="place-list-page">
        <div className="error">존재하지 않는 카테고리입니다.</div>
      </div>
    );
  }

  return (
    <div className="place-list-page">
      <header className="page-header">
        <div className="header-left">
          <Button variant="secondary" onClick={handleBackToCategories}>
            ← 카테고리 목록으로
          </Button>
          <div className="category-info">
            <span className="category-icon">{currentCategory.icon}</span>
            <h1>{currentCategory.name} 관리</h1>
          </div>
        </div>
        <Button variant="primary" onClick={handleAddPlace}>
          + 여행지 등록
        </Button>
      </header>

      <PlaceList
        places={places}
        onEditPlace={handleEditPlace}
        onDeletePlace={handleDeletePlace}
        loading={loading}
        error={error}
      />
    </div>
  );
};

export default PlaceListPage;
