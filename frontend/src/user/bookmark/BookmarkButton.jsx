// src/components/BookmarkButton.jsx
import React, { useState, useEffect } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { apiRequest } from '../utils/api'; // 프로젝트 구조에 맞게 경로 조정
import './BookmarkButton.css'; // optional 스타일 (아래에 제공)

export default function BookmarkButton({
  placeId,
  initialBookmarked = false,
  initialBookmarkId = null,
  onChange, // (bookmarked:boolean, bookmarkId|null) => void
  className = '',
}) {
  const [bookmarked, setBookmarked] = useState(initialBookmarked);
  const [bookmarkId, setBookmarkId] = useState(initialBookmarkId);
  const [busy, setBusy] = useState(false);

  // 초기 prop 변경에 반응
  useEffect(() => {
    setBookmarked(initialBookmarked);
  }, [initialBookmarked]);

  useEffect(() => {
    setBookmarkId(initialBookmarkId);
  }, [initialBookmarkId]);

  const create = async () => {
    setBusy(true);
    try {
      const res = await apiRequest('http://localhost:8080/api/bookmarks', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ placeId }),
      });

      if (!res.ok) {
        const text = await res.text().catch(() => '');
        throw new Error(text || '북마크 추가 실패');
      }

      const json = await res.json().catch(() => null);
      // ApiResponse: { data: BookmarkResponseDto } or the dto directly
      const data = (json && (json.data ?? json)) ?? null;
      const newId = data?.bookmarkId ?? data?.id ?? null;

      setBookmarked(true);
      setBookmarkId(newId);
      toast.success('북마크에 추가되었습니다.');
      onChange && onChange(true, newId);
    } catch (err) {
      console.error('create bookmark error', err);
      toast.error(err.message || '북마크 추가에 실패했습니다.');
    } finally {
      setBusy(false);
    }
  };

  const remove = async (id) => {
    setBusy(true);
    try {
      const res = await apiRequest(`http://localhost:8080/api/bookmarks/${id}`, {
        method: 'DELETE',
      });

      if (!res.ok) {
        const text = await res.text().catch(() => '');
        throw new Error(text || '북마크 삭제 실패');
      }

      // 성공
      setBookmarked(false);
      setBookmarkId(null);
      toast.success('북마크가 삭제되었습니다.');
      onChange && onChange(false, null);
    } catch (err) {
      console.error('delete bookmark error', err);
      toast.error(err.message || '북마크 삭제에 실패했습니다.');
    } finally {
      setBusy(false);
    }
  };

  const handleClick = async () => {
    if (busy) return;
    if (bookmarked) {
      // 삭제하려면 bookmarkId 필요
      if (!bookmarkId) {
        toast.error('삭제할 북마크 ID가 없습니다.');
        return;
      }
      await remove(bookmarkId);
    } else {
      await create();
    }
  };

  return (
    <>
      <ToastContainer position="top-right" autoClose={2000} />
      <button
        onClick={handleClick}
        disabled={busy}
        className={`bookmark-btn ${bookmarked ? 'bookmarked' : ''} ${busy ? 'busy' : ''} ${className}`}
        aria-pressed={bookmarked}
        title={bookmarked ? '북마크 해제' : '북마크 추가'}
      >
        {busy ? '처리 중...' : (bookmarked ? '✔ 저장됨' : '☆ 저장')}
      </button>
    </>
  );
}
