import api from "./api";

// 리뷰 등록
export const createReview = async (reviewData) => {
  try {
    const response = await api.post("/api/review/add", reviewData);
    return response;
  } catch (error) {
    console.error("리뷰 등록 실패:", error);
    throw error;
  }
};

// 리뷰 수정
export const modifyReview = async (memberId, rating) => {
  try {
    const token = localStorage.getItem("accessToken");
    const response = await api.patch(
      `/api/review/modify/${memberId}?modifyRating=${rating}`,
      {},
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );
    return response;
  } catch (error) {
    console.error("리뷰 수정 실패:", error);
    throw error;
  }
};

// 리뷰 삭제
export const deleteReview = async (reviewId) => {
  try {
    const response = await api.delete(`/api/review/delete/${reviewId}`);
    return response;
  } catch (error) {
    console.error("리뷰 삭제 실패:", error);
    throw error;
  }
};

// 내가 작성한 리뷰 조회
export const getMyReview = async (reviewId) => {
  try {
    const response = await api.get(`/api/review/${reviewId}`);
    return response;
  } catch (error) {
    console.error("내 리뷰 조회 실패:", error);
    throw error;
  }
};

// 특정 여행지의 리뷰 조회
export const getPlaceReviews = async (placeId) => {
  try {
    const response = await api.get(`/api/review/list/${placeId}`);
    return response;
  } catch (error) {
    console.error("여행지 리뷰 조회 실패:", error);
    throw error;
  }
};

export const getAllReviews = async () => {
  const response = await api.get("/api/review/myReview");
  return response.data;
};


// 추천 리뷰 (평균 별점 상위 5개의 여행지)
export const getRecommendedReviews = async (placeId) => {
  try {
    const response = await api.get(`/api/review/recommend/${placeId}`);
    return response;
  } catch (error) {
    console.error("추천 리뷰 조회 실패:", error);
    throw error;
  }
};