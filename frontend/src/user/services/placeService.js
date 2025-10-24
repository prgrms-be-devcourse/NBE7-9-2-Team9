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

// 특정 카테고리의 장소 목록 조회
export const getPlacesByCategory = async (categoryId) => {
  try {
    const response = await api.get(`/api/place/category/${categoryId}`);
    return response;
  } catch (error) {
    console.error("카테고리별 장소 목록 조회 실패:", error);
    throw error;
  }
};

// 전체 장소 목록 조회 (모든 카테고리)
export const getAllPlaces = async () => {
  try {
    // 모든 카테고리의 장소를 가져오기 위해 카테고리 목록을 먼저 조회
    const categories = await getCategories();
    const allPlaces = [];

    for (const category of categories.data) {
      const places = await getPlacesByCategory(category.id);
      allPlaces.push(...places.data);
    }

    return allPlaces; // ✅ 핵심 수정 (배열만 반환)
  } catch (error) {
    console.error("전체 장소 목록 조회 실패:", error);
    throw error;
  }
};
