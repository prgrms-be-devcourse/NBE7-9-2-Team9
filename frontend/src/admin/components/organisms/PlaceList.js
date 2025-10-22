import React, { useState, useMemo } from "react";
import PlaceCard from "../molecules/PlaceCard";
import SearchBar from "../molecules/SearchBar";
import "./PlaceList.css";

const PlaceList = ({
  places,
  onEditPlace,
  onDeletePlace,
  loading = false,
  error = null,
}) => {
  const [searchTerm, setSearchTerm] = useState("");

  // 검색 필터링 로직
  const filteredPlaces = useMemo(() => {
    if (!places || !searchTerm) return places || [];

    const term = searchTerm.toLowerCase();
    return places.filter(
      (place) =>
        place.placeName?.toLowerCase().includes(term) ||
        place.address?.toLowerCase().includes(term) ||
        place.gu?.toLowerCase().includes(term) ||
        place.description?.toLowerCase().includes(term)
    );
  }, [places, searchTerm]);
  if (loading) {
    return (
      <div className="place-list">
        <div className="loading">여행지 목록을 불러오는 중...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="place-list">
        <div className="error">오류: {error}</div>
      </div>
    );
  }

  if (!places || places.length === 0) {
    return (
      <div className="place-list">
        <div className="empty">
          <h3>등록된 여행지가 없습니다</h3>
          <p>새로운 여행지를 등록해보세요.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="place-list">
      <div className="place-list-header">
        <h2>여행지 목록 ({places.length}개)</h2>
        <SearchBar
          value={searchTerm}
          onChange={setSearchTerm}
          placeholder="여행지명, 주소, 구, 설명으로 검색..."
        />
      </div>

      {searchTerm && (
        <div className="search-results-info">
          <p>
            "{searchTerm}" 검색 결과: {filteredPlaces.length}개
            {filteredPlaces.length === 0 && " (검색 결과가 없습니다)"}
          </p>
        </div>
      )}

      <div className="place-grid">
        {filteredPlaces.map((place) => (
          <PlaceCard
            key={place.id}
            place={place}
            onEdit={onEditPlace}
            onDelete={onDeletePlace}
          />
        ))}
      </div>

      {searchTerm && filteredPlaces.length === 0 && (
        <div className="no-results">
          <h3>검색 결과가 없습니다</h3>
          <p>다른 검색어를 시도해보세요.</p>
        </div>
      )}
    </div>
  );
};

export default PlaceList;
