// 📁 src/utils/api.js
export async function apiRequest(url, options = {}) {
  const token = localStorage.getItem("accessToken");

  const headers = {
    "Content-Type": "application/json",
    ...(token && { Authorization: `Bearer ${token}` }),
    ...options.headers,
  };

  const config = {
    ...options,
    headers,
    credentials: "include", // ✅ refreshToken 쿠키 포함
  };

  let response = await fetch(url, config);

  // ✅ AccessToken 만료 시 재발급 시도
  if (response.status === 401) {
    console.warn("⚠️ AccessToken 만료 → 재발급 시도");

    const reissue = await fetch("http://localhost:8080/api/auth/reissue", {
      method: "POST",
      credentials: "include",
    });

    if (reissue.ok) {
      const data = await reissue.json();
      const newToken = data.data.accessToken;

      // ✅ 새 AccessToken 저장 후 재요청
      localStorage.setItem("accessToken", newToken);
      console.log("🔁 새 토큰 재발급 완료");

      const retry = await fetch(url, {
        ...config,
        headers: {
          ...headers,
          Authorization: `Bearer ${newToken}`,
        },
      });
      return retry;
    } else {
      console.error("❌ 토큰 재발급 실패 → 로그인 필요");
      localStorage.removeItem("accessToken");
      window.location.href = "/user/member/login";
    }
  }

  return response;
}