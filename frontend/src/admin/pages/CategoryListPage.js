import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import CategoryCard from "../components/molecules/CategoryCard";
import { getCategories } from "../services/categoryService";
import "./CategoryListPage.css";

const CategoryListPage = () => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      setLoading(true);
      // 임시 데이터 (백엔드 API 연결 전까지)
      const mockCategories = [
        {
          id: 1,
          name: "관광지",
          description: "명소, 박물관, 전시관 등",
          icon: "🏛️",
          placeCount: 12,
        },
        {
          id: 2,
          name: "맛집",
          description: "레스토랑, 카페, 음식점 등",
          icon: "🍽️",
          placeCount: 8,
        },
        {
          id: 3,
          name: "숙소",
          description: "호텔, 펜션, 게스트하우스 등",
          icon: "🏨",
          placeCount: 15,
        },
      ];

      setCategories(mockCategories);
      setError(null);
    } catch (err) {
      setError("카테고리 목록을 불러오는데 실패했습니다.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const handleCategoryClick = (category) => {
    navigate(`/admin/places/${category.id}`);
  };

  if (loading) {
    return (
      <div className="category-list-page">
        <div className="loading">로딩 중...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="category-list-page">
        <div className="error">{error}</div>
      </div>
    );
  }

  return (
    <div className="category-list-page">
      <header className="page-header">
        <h1>관리자 페이지</h1>
        <p>카테고리를 선택하여 여행지를 관리하세요</p>
      </header>

      <div className="categories-grid">
        {categories.map((category) => (
          <CategoryCard
            key={category.id}
            category={category}
            onClick={handleCategoryClick}
          />
        ))}
      </div>
    </div>
  );
};

export default CategoryListPage;
