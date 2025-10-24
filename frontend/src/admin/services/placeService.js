import api from "./api";

// 여행지 목록 조회 (전체) - 관리자용
export const getPlaces = async () => {
  try {
    const response = await api.get("/api/admin/places");
    return response;
  } catch (error) {
    console.error("여행지 목록 조회 실패:", error);
    throw error;
  }
};

// 여행지 상세 조회 - 관리자용
export const getPlace = async (id) => {
  try {
    const response = await api.get(`/api/admin/places/${id}`);
    return response;
  } catch (error) {
    console.error("여행지 상세 조회 실패:", error);
    throw error;
  }
};

// 여행지 등록 - 관리자용
export const createPlace = async (placeData) => {
  try {
    const response = await api.post("/api/admin/places", placeData);
    return response;
  } catch (error) {
    console.error("여행지 등록 실패:", error);
    throw error;
  }
};

// 여행지 수정 - 관리자용
export const updatePlace = async (id, placeData) => {
  try {
    const response = await api.put(`/api/admin/places/${id}`, placeData);
    return response;
  } catch (error) {
    console.error("여행지 수정 실패:", error);
    throw error;
  }
};

// 여행지 삭제 - 관리자용
export const deletePlace = async (id) => {
  try {
    const response = await api.delete(`/api/admin/places/${id}`);
    return response;
  } catch (error) {
    console.error("여행지 삭제 실패:", error);
    throw error;
  }
};
