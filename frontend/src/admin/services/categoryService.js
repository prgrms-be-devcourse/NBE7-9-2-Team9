import api from "./api";

// 카테고리 목록 조회
export const getCategories = async () => {
  try {
    const response = await api.get("/api/categories");
    return response;
  } catch (error) {
    console.error("카테고리 목록 조회 실패:", error);
    throw error;
  }
};

// 카테고리별 여행지 목록 조회
export const getPlacesByCategory = async (categoryId) => {
  try {
    const response = await api.get(`/api/place/category/${categoryId}`);
    return response;
  } catch (error) {
    console.error("카테고리별 여행지 목록 조회 실패:", error);
    throw error;
  }
};
