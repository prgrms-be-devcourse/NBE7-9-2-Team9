import React from "react";
import PlaceCard from "../molecules/PlaceCard";
import "./PlaceList.css";

const PlaceList = ({
  places,
  onEditPlace,
  onDeletePlace,
  loading = false,
  error = null,
}) => {
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
      </div>
      <div className="place-grid">
        {places.map((place) => (
          <PlaceCard
            key={place.id}
            place={place}
            onEdit={onEditPlace}
            onDelete={onDeletePlace}
          />
        ))}
      </div>
    </div>
  );
};

export default PlaceList;
