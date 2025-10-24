import React, { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { apiRequest } from '../../utils/api.js'; 

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080';

// ---------------------
// Backend calls (apiRequest 사용)
// ---------------------
async function fetchBookmarks() {
  const res = await apiRequest(`${API_BASE}/api/bookmarks`, { method: 'GET' });
  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(text || '북마크 목록 불러오기 실패');
  }
  const json = await res.json().catch(() => null);
  // ApiResponse 패턴: { data: [...] } 또는 원본 배열
  return (json && (json.data ?? json)) ?? [];
}

async function deleteBookmark(bookmarkId) {
  const res = await apiRequest(`${API_BASE}/api/bookmarks/${bookmarkId}`, { method: 'DELETE' });
  if (!res.ok) {
    const text = await res.text().catch(() => '');
    throw new Error(text || '북마크 삭제 실패');
  }
  const json = await res.json().catch(() => null);
  return (json && (json.data ?? json)) ?? null;
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
        // data might be array or { items: [...] }
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

      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: 16 }}>
        <h2 style={{ fontSize: 20, fontWeight: 600 }}>내 북마크</h2>
        <div style={{ fontSize: 13, color: '#6b7280' }}>총 {bookmarks.length}개</div>
      </div>

      {loading ? (
        <div style={{ display: 'grid', gridTemplateColumns: 'repeat(1, 1fr)', gap: 12 }}>
          {Array.from({ length: 6 }).map((_, i) => (
            <div key={i} style={{ padding: 12, borderRadius: 8, background: '#f3f4f6', height: 112 }} />
          ))}
        </div>
      ) : error ? (
        <div style={{ padding: 16, border: '1px solid #fee2e2', borderRadius: 8, color: '#b91c1c' }}>
          에러: {error}
        </div>
      ) : bookmarks.length === 0 ? (
        <div style={{ padding: 32, textAlign: 'center', color: '#6b7280' }}>
          아직 저장한 장소가 없습니다. 여행지를 보며 마음에 드는 곳을 저장해보세요!
        </div>
      ) : (
        <ul style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(240px, 1fr))', gap: 12 }}>
          {bookmarks.map((b) => (
            <li key={b.bookmarkId} style={{ border: '1px solid #e5e7eb', borderRadius: 8, padding: 12, background: '#fff', boxShadow: '0 1px 2px rgba(0,0,0,0.03)' }}>
              <div style={{ fontWeight: 600, marginBottom: 6 }}>{b.placeName ?? b.title}</div>
              {b.address && <div style={{ fontSize: 12, color: '#6b7280', marginBottom: 8 }}>{b.address}</div>}
              {b.description && <div style={{ fontSize: 12, color: '#6b7280', marginBottom: 8, display: '-webkit-box', WebkitLineClamp: 2, WebkitBoxOrient: 'vertical', overflow: 'hidden' }}>{b.description}</div>}

              <div style={{ display: 'flex', justifyContent: 'flex-end', marginTop: 8 }}>
                <button
                  onClick={() => handleDelete(b)}
                  disabled={!!busyIds[b.bookmarkId]}
                  title="북마크 삭제"
                  style={{
                    padding: '6px 10px',
                    borderRadius: 6,
                    border: '1px solid #e5e7eb',
                    background: busyIds[b.bookmarkId] ? '#f3f4f6' : '#fff',
                    cursor: busyIds[b.bookmarkId] ? 'not-allowed' : 'pointer',
                    fontSize: 12
                  }}
                >
                  {busyIds[b.bookmarkId] ? '삭제중...' : '삭제'}
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
