import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import PlaceList from "../components/organisms/PlaceList";
import Button from "../components/atoms/Button";
import { getPlacesByCategory } from "../services/categoryService";
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
      // 임시 데이터 (백엔드 API 연결 전까지)
      const mockPlaces = [
        {
          id: 1,
          placeName: "경복궁",
          address: "서울특별시 종로구 사직로 161",
          gu: "종로구",
          description:
            "조선왕조의 대표적인 궁궐로, 대한민국의 대표적인 역사 문화재입니다.",
          ratingAvg: 4.5,
          ratingCount: 1234,
          category: { id: 1, name: "관광지" },
        },
        {
          id: 2,
          placeName: "명동",
          address: "서울특별시 중구 명동",
          gu: "중구",
          description: "서울의 대표적인 상업지구이자 관광지입니다.",
          ratingAvg: 4.2,
          ratingCount: 856,
          category: { id: 1, name: "관광지" },
        },
        {
          id: 3,
          placeName: "롯데월드타워",
          address: "서울특별시 송파구 올림픽로 300",
          gu: "송파구",
          description:
            "서울의 랜드마크인 롯데월드타워와 롯데월드가 있는 복합문화공간입니다.",
          ratingAvg: 4.3,
          ratingCount: 2156,
          category: { id: 1, name: "관광지" },
        },
      ];

      // 카테고리별 필터링 (임시로 카테고리 1번만)
      const filteredPlaces = categoryId === "1" ? mockPlaces : [];

      setPlaces(filteredPlaces);
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
    // 추후 구현
    console.log("여행지 등록");
  };

  const handleEditPlace = (place) => {
    // 추후 구현
    console.log("여행지 수정:", place);
  };

  const handleDeletePlace = (place) => {
    // 추후 구현
    if (window.confirm(`"${place.placeName}"을(를) 정말 삭제하시겠습니까?`)) {
      console.log("여행지 삭제:", place);
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
