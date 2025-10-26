import React, { useEffect, useState } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { apiRequest, showErrorToast } from '../../utils/api.js';
import PlanDetailModal from './PlanDetailModal.jsx';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export default function BookmarkApp() {
  const [bookmarks, setBookmarks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [busyIds, setBusyIds] = useState({});
  const [showModal, setShowModal] = useState(false);
  const [selectedBookmark, setSelectedBookmark] = useState(null);

  // --- 북마크 불러오기 ---
  useEffect(() => {
    setLoading(true);
    apiRequest(`${API_BASE}/api/bookmarks`, { method: 'GET' })
      .then(async (res) => {
        if (!res.ok) throw res;
        const data = await res.json();
        setBookmarks(data.data ?? data);
      })
      .catch(async (err) => await showErrorToast(err, toast))
      .finally(() => setLoading(false));
  }, []);

  // --- 북마크 삭제 ---
  const handleDelete = async (bookmark) => {
    const bookmarkId = bookmark.bookmarkId;
    if (!bookmarkId || busyIds[bookmarkId]) return;

    setBusyIds((s) => ({ ...s, [bookmarkId]: true }));
    const prev = bookmarks;
    setBookmarks((list) => list.filter((b) => b.bookmarkId !== bookmarkId));

    try {
      const res = await apiRequest(`${API_BASE}/api/bookmarks/${bookmarkId}`, { method: 'DELETE' });
      if (!res.ok) throw new Error(await res.text());
      toast.success('북마크가 삭제되었습니다.');
    } catch (err) {
      setBookmarks(prev);
      await showErrorToast(err,toast);
    } finally {
      setBusyIds((s) => { const n = { ...s }; delete n[bookmarkId]; return n; });
    }
  };

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <ToastContainer position="top-right" autoClose={2500} />
      <h2 className="text-lg font-semibold mb-4">내 북마크</h2>

      {loading ? (
        <div>로딩 중...</div>
      ) : error ? (
        <div className="text-red-600">{error}</div>
      ) : bookmarks.length === 0 ? (
        <div className="text-gray-500">저장된 북마크가 없습니다.</div>
      ) : (
        <ul style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(240px, 1fr))', gap: 12 }}>
          {bookmarks.map((b) => (
            <li key={b.bookmarkId} className="border rounded p-2 bg-white shadow-sm">
              <div className="font-semibold">{b.placeName ?? b.title}</div>
              {b.address && <div className="text-sm text-gray-500">{b.address}</div>}
              <div className="flex justify-end mt-2 space-x-2">
                <button
                  onClick={() => handleDelete(b)}
                  disabled={!!busyIds[b.bookmarkId]}
                  className="px-2 py-1 border rounded text-sm"
                >
                  {busyIds[b.bookmarkId] ? '삭제중...' : '삭제'}
                </button>
                <button
                  onClick={() => { setSelectedBookmark(b); setShowModal(true); }}
                  className="px-2 py-1 border rounded text-sm bg-blue-100"
                >
                  추가하기
                </button>
              </div>
            </li>
          ))}
        </ul>
      )}

      {showModal && selectedBookmark && (
        <PlanDetailModal
          bookmark={selectedBookmark}
          onClose={() => { setShowModal(false); setSelectedBookmark(null); }}
        />
      )}
    </div>
  );
}