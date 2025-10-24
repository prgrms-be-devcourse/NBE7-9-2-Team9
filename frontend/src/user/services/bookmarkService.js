import api from "../../admin/services/api";

// 북마크 목록 조회
export const getBookmarks = async () => {
  try {
    const response = await api.get("/api/bookmarks");
    return response;
  } catch (error) {
    console.error("북마크 목록 조회 실패:", error);
    throw error;
  }
};

// 북마크 추가
export const addBookmark = async (placeId) => {
  try {
    const response = await api.post("/api/bookmarks", { placeId });
    return response;
  } catch (error) {
    console.error("북마크 추가 실패:", error);
    throw error;
  }
};

// 북마크 삭제
export const removeBookmark = async (placeId) => {
  try {
    const response = await api.delete(`/api/bookmarks/${placeId}`);
    return response;
  } catch (error) {
    console.error("북마크 삭제 실패:", error);
    throw error;
  }
};
