import React, { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080';

// ---------------------
// Auth + apiFetch util
// ---------------------
function readTokenFromStorage() {
  return localStorage.getItem('accessToken');
}

function getAuthHeader() {
  const raw = readTokenFromStorage();
  if (!raw) return {};
  if (raw.startsWith('Bearer ')) {
    return { Authorization: raw };
  }
  return { Authorization: `Bearer ${raw}` };
}

async function apiFetch(url, options = {}) {
  const headers = {
    'Content-Type': 'application/json',
    ...(options.headers || {}),
    ...getAuthHeader(),
  };

  const res = await fetch(url, { ...options, headers, credentials: 'include' });

  if (res.status === 401) {
    toast.error('로그인이 필요합니다. 로그인 페이지로 이동합니다.');
    window.location.href = '/login';
    throw new Error('Unauthorized');
  }

  const text = await res.text();
  let json = null;
  try {
    json = text ? JSON.parse(text) : null;
  } catch (e) {
    if (!res.ok) {
      const msg = res.statusText || '서버 에러';
      toast.error(msg);
      throw new Error(msg);
    }
    return text;
  }

  if (!res.ok) {
    const code = json?.code;
    const message = json?.message || json?.error || res.statusText || '서버 에러';
    if (code === 'TOKEN_NOT_FOUND') {
      toast.warn('요청에 토큰이 존재하지 않습니다. 로그인 후 다시 시도해주세요.');
      window.location.href = '/login';
      throw new Error(message);
    }
    toast.error(message);
    throw new Error(message);
  }

  if (json && json.data !== undefined) return json.data;
  return json;
}

// ---------------------
// Backend calls
// ---------------------
async function fetchBookmarks() {
  return apiFetch(`${API_BASE}/api/bookmarks`, { method: 'GET' });
}

async function deleteBookmark(bookmarkId) {
  return apiFetch(`${API_BASE}/api/bookmarks/${bookmarkId}`, { method: 'DELETE' });
}

// ----------------------------------------
// BookmarkFeature
// ----------------------------------------
export default function BookmarkFeature() {
  const [bookmarks, setBookmarks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [busyIds, setBusyIds] = useState({});

  useEffect(() => {
    let mounted = true;
    setLoading(true);
    setError(null);
    fetchBookmarks()
      .then((data) => {
        if (!mounted) return;
        const list = Array.isArray(data) ? data : (data.items ?? data);
        setBookmarks(list || []);
      })
      .catch((err) => {
        if (!mounted) return;
        setError(err.message || '목록을 불러올 수 없습니다.');
      })
      .finally(() => mounted && setLoading(false));
    return () => { mounted = false; };
  }, []);

  const handleDelete = async (bookmark) => {
    const bookmarkId = bookmark.bookmarkId;
    if (!bookmarkId) {
      toast.error('삭제할 북마크 ID가 없습니다.');
      return;
    }
    if (busyIds[bookmarkId]) return;
    setBusyIds((s) => ({ ...s, [bookmarkId]: true }));
    const prev = bookmarks;
    setBookmarks((list) => list.filter((b) => b.bookmarkId !== bookmarkId));
    try {
      await deleteBookmark(bookmarkId);
      toast.success('북마크가 삭제되었습니다.');
    } catch (err) {
      setBookmarks(prev);
      toast.error(err.message || '삭제에 실패했습니다.');
    } finally {
      setBusyIds((s) => { const n = { ...s }; delete n[bookmarkId]; return n; });
    }
  };

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <ToastContainer position="top-right" autoClose={2500} />

      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-semibold">내 북마크</h2>
        <div className="text-sm text-slate-500">총 {bookmarks.length}개</div>
      </div>

      {loading ? (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
          {Array.from({ length: 6 }).map((_, i) => (
            <div key={i} className="animate-pulse p-3 border rounded-md bg-white/50 h-28" />
          ))}
        </div>
      ) : error ? (
        <div className="p-6 border rounded text-red-600">에러: {error}</div>
      ) : bookmarks.length === 0 ? (
        <div className="p-8 text-center text-slate-600">아직 저장한 장소가 없습니다. 여행지를 보며 마음에 드는 곳을 저장해보세요!</div>
      ) : (
        <ul className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4">
          {bookmarks.map((b) => (
            <li key={b.id} className="border rounded-lg overflow-hidden bg-white shadow-sm p-3">
              <div className="font-medium text-sm mb-1">{b.title || b.placeName}</div>
              {b.description && <div className="text-xs text-slate-500 mb-1 line-clamp-2">{b.description}</div>}
              {b.gu && <div className="text-xs text-slate-400">{b.gu}</div>}
              {b.address && <div className="text-xs text-slate-400">{b.address}</div>}

              <div className="flex justify-end mt-2">
                <button
                  onClick={() => handleDelete(b)}
                  disabled={!!busyIds[b.bookmarkId]}
                  className="px-2 py-1 text-xs rounded border disabled:opacity-50"
                  title="북마크 삭제"
                >
                  {busyIds[b.id] ? '삭제중...' : '삭제'}
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
