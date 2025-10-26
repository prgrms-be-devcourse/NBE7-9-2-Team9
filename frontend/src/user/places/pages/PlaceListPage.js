import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getPlacesByCategory } from "../../services/categoryService";
import {
  addBookmark,
  removeBookmark,
  getBookmarks,
} from "../../services/bookmarkService";
import "./PlaceListPage.css";

const PlaceListPage = () => {
  const { categoryId } = useParams();
  const navigate = useNavigate();
  const [places, setPlaces] = useState([]);
  const [filteredPlaces, setFilteredPlaces] = useState([]);
  const [bookmarks, setBookmarks] = useState(new Set());
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    fetchPlaces();
    fetchBookmarks();
  }, [categoryId]);

  // 검색 기능
  useEffect(() => {
    if (searchTerm.trim() === "") {
      setFilteredPlaces(places);
    } else {
      const filtered = places.filter((place) => {
        const name = (place.placeName || "").toLowerCase();
        const address = (place.address || "").toLowerCase();
        const gu = (place.gu || "").toLowerCase();
        const search = searchTerm.toLowerCase();

        return (
          name.includes(search) ||
          address.includes(search) ||
          gu.includes(search)
        );
      });
      setFilteredPlaces(filtered);
    }
  }, [places, searchTerm]);

  const fetchPlaces = async () => {
    try {
      setLoading(true);
      const response = await getPlacesByCategory(categoryId);

      // 별점순으로 정렬 (높은 별점부터)
      const sortedPlaces = response.data.sort(
        (a, b) => (b.ratingAvg || 0) - (a.ratingAvg || 0)
      );
      setPlaces(sortedPlaces);
      setError(null);
    } catch (err) {
      console.error("여행지 목록 조회 오류:", err);
      setError("여행지 목록을 불러오는데 실패했습니다.");
    } finally {
      setLoading(false);
    }
  };

  const fetchBookmarks = async () => {
    try {
      const response = await getBookmarks();
      const bookmarkIds = new Set(
        response.data.map((bookmark) => bookmark.placeId)
      );
      setBookmarks(bookmarkIds);
    } catch (err) {
      console.error("북마크 목록 조회 오류:", err);
    }
  };

  const handleBookmarkToggle = async (placeId) => {
    try {
      if (bookmarks.has(placeId)) {
        // 북마크 제거
        await removeBookmark(placeId);
        setBookmarks((prev) => {
          const newSet = new Set(prev);
          newSet.delete(placeId);
          return newSet;
        });
        alert("북마크에서 제거되었습니다.");
      } else {
        // 북마크 추가
        await addBookmark(placeId);
        setBookmarks((prev) => new Set([...prev, placeId]));
        alert("북마크에 추가되었습니다.");
      }
    } catch (err) {
      console.error("북마크 토글 오류:", err);
      alert("북마크 처리 중 오류가 발생했습니다.");
    }
  };

  const getCategoryName = (categoryId) => {
    const categoryMap = {
      1: "관광지",
      2: "맛집",
      3: "야경명소",
      4: "숙소",
    };
    return categoryMap[categoryId] || "여행지";
  };

  const renderStars = (rating) => {
    const stars = [];
    const safeRating = rating || 0;
    const fullStars = Math.floor(safeRating);
    const hasHalfStar = safeRating % 1 !== 0;

    for (let i = 0; i < fullStars; i++) {
      stars.push(
        <span key={i} className="star filled">
          ⭐
        </span>
      );
    }

    if (hasHalfStar) {
      stars.push(
        <span key="half" className="star half">
          ⭐
        </span>
      );
    }

    const emptyStars = 5 - Math.ceil(safeRating);
    for (let i = 0; i < emptyStars; i++) {
      stars.push(
        <span key={`empty-${i}`} className="star empty">
          ☆
        </span>
      );
    }

    return stars;
  };

  if (loading) return <div className="loading">여행지를 불러오는 중...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="place-list-page">
      <header className="page-header">
        <button
          className="back-button"
          onClick={() => navigate("/user/places")}
        >
          ← 뒤로가기
        </button>
        <div className="header-content">
          <h1>{getCategoryName(categoryId)}</h1>
          <p>{filteredPlaces.length}개의 여행지가 있습니다</p>
        </div>
      </header>

      <div className="search-container">
        <div className="search-box">
          <input
            type="text"
            placeholder="여행지명, 주소, 구로 검색하세요..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            className="search-input"
          />
          <div className="search-icon">🔍</div>
        </div>
      </div>

      <div className="places-container">
        {filteredPlaces.length > 0 ? (
          <div className="places-grid">
            {filteredPlaces.map((place) => (
              <div key={place.id} className="place-card">
                <div className="place-header">
                  <h3 className="place-name">
                    {place.placeName || "여행지명 없음"}
                  </h3>
                  <button
                    className={`bookmark-button ${
                      bookmarks.has(place.id) ? "bookmarked" : ""
                    }`}
                    onClick={() => handleBookmarkToggle(place.id)}
                    title={
                      bookmarks.has(place.id)
                        ? "북마크에서 제거"
                        : "북마크에 추가"
                    }
                  >
                    {bookmarks.has(place.id) ? "❤️" : "🤍"}
                  </button>
                </div>

                <div className="place-info">
                  <p className="place-address">
                    📍 {place.address || "주소 정보 없음"}
                  </p>
                  <p className="place-gu">🏘️ {place.gu || "구 정보 없음"}</p>
                </div>

                <div className="place-rating">
                  <div className="stars">{renderStars(place.ratingAvg)}</div>
                  <span className="rating-text">
                    {(place.ratingAvg || 0).toFixed(1)} (
                    {place.ratingCount || 0}개 리뷰)
                  </span>
                </div>

                {place.description && (
                  <div className="place-description">
                    <p>{place.description}</p>
                  </div>
                )}
              </div>
            ))}
          </div>
        ) : (
          <div className="no-results">
            <div className="no-results-icon">🔍</div>
            <h3>검색 결과가 없습니다</h3>
            <p>다른 검색어로 시도해보세요.</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default PlaceListPage;
