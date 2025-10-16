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
      const response = await getCategories();

      // 백엔드 응답 데이터를 프론트엔드 형태로 변환
      const categoriesWithIcons = response.data.map((category) => ({
        ...category,
        description: getCategoryDescription(category.name),
        icon: getCategoryIcon(category.name),
        placeCount: 0, // 추후 실제 카운트로 변경
      }));

      setCategories(categoriesWithIcons);
      setError(null);
    } catch (err) {
      setError("카테고리 목록을 불러오는데 실패했습니다.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const getCategoryDescription = (name) => {
    const descriptions = {
      맛집: "레스토랑, 카페, 음식점 등",
      관광지: "명소, 박물관, 전시관 등",
      숙소: "호텔, 펜션, 게스트하우스 등",
    };
    return descriptions[name] || "카테고리 설명";
  };

  const getCategoryIcon = (name) => {
    const icons = {
      맛집: "🍽️",
      관광지: "🏛️",
      숙소: "🏨",
    };
    return icons[name] || "📁";
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
