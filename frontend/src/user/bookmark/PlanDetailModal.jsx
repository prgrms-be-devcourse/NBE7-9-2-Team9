import React, { useState, useEffect } from 'react';
import { apiRequest , showErrorToast } from '../../utils/api.js';
import { toast } from 'react-toastify';
import './PlanModal.css';

const API_BASE = process.env.REACT_APP_API_URL || 'http://localhost:8080';

export default function PlanDetailModal({ bookmark, onClose }) {
  const [plans, setPlans] = useState([]);
  const [selectedPlanId, setSelectedPlanId] = useState('');
  const [detailData, setDetailData] = useState({
    placeId: bookmark.placeId ?? bookmark.bookmarkId,
    placeName: bookmark.placeName ?? bookmark.title,
    startTime: '',
    endTime: '',
    title: '',
    content: ''
  });
  const [loading, setLoading] = useState(false);
  const formatDateTimeForBackend = (str) => {
    if (!str) return null;
    // 입력값: "2025-10-24T19:30" → "2025-10-24T19:30:00"
    return str.length === 16 ? str + ':00' : str;
  };
  // --- 계획 목록 불러오기 ---
  useEffect(() => {
    apiRequest(`${API_BASE}/api/plan/list`, { method: 'GET' })
      .then(async (res) => {
        if (!res.ok) throw new Error(await res.text());
        const data = await res.json();
        setPlans(data.data ?? data);
      })
      .catch((err) => toast.error(err.message));
  }, []);

  // --- 상세 일정 추가 ---
  const handleAddDetail = async () => {
    if (!selectedPlanId) {
      toast.error('계획을 선택해주세요.');
      return;
    }

    // 선택한 plan 객체 찾기
    const selectedPlan = plans.find(p => p.id === parseInt(selectedPlanId));
    if (!selectedPlan) {
      toast.error('선택한 계획을 찾을 수 없습니다.');
      return;
    }

    const payload = {
      planId: parseInt(selectedPlanId),
      placeId: detailData.placeId,
      startTime: detailData.startTime,
      endTime: detailData.endTime,
      title: detailData.title,
      content: detailData.content
    };

    try {
        setLoading(true);
        const res = await apiRequest(`${API_BASE}/api/plan/detail/add`, {
          method: 'POST',
          body: JSON.stringify(payload)
        });
        if (!res.ok) throw res; // Response 객체를 throw
        toast.success('상세 일정이 추가되었습니다.');
        onClose();
      } catch (err) {
        await showErrorToast(err, toast);
      } finally {
        setLoading(false);
      }
  };

  return (
    <div className="modal-backdrop">
      <div className="modal-box">
        <h2 className="font-semibold mb-2">계획 상세 추가</h2>

        <div className="mb-3">
          <label className="block mb-1">계획 선택</label>
          <select
            value={selectedPlanId}
            onChange={(e) => setSelectedPlanId(e.target.value)}
            className="w-full border rounded p-1"
          >
            <option value="">계획을 선택하세요</option>
            {plans.map((p) => (
              <option key={p.id} value={p.id}>{p.title}</option>
            ))}
          </select>
        </div>

        <div className="mb-3">
          <label className="block mb-1">장소</label>
          <input
            type="text"
            value={detailData.placeName}
            readOnly
            className="w-full border rounded p-1 bg-gray-100"
          />
        </div>

        <div className="mb-3">
          <label className="block mb-1">시작 시간</label>
          <input
            type="datetime-local"
            value={detailData.startTime}
            onChange={(e) => setDetailData({ ...detailData, startTime: e.target.value })}
            className="w-full border rounded p-1"
          />
        </div>

        <div className="mb-3">
          <label className="block mb-1">종료 시간</label>
          <input
            type="datetime-local"
            value={detailData.endTime}
            onChange={(e) => setDetailData({ ...detailData, endTime: e.target.value })}
            className="w-full border rounded p-1"
          />
        </div>

        <div className="mb-3">
          <label className="block mb-1">제목</label>
          <input
            type="text"
            value={detailData.title}
            onChange={(e) => setDetailData({ ...detailData, title: e.target.value })}
            className="w-full border rounded p-1"
          />
        </div>

        <div className="mb-3">
          <label className="block mb-1">내용</label>
          <textarea
            value={detailData.content}
            onChange={(e) => setDetailData({ ...detailData, content: e.target.value })}
            className="w-full border rounded p-1"
            rows={3}
          />
        </div>

        <div className="flex justify-end space-x-2">
          <button onClick={onClose} className="px-3 py-1 border rounded">취소</button>
          <button
            onClick={handleAddDetail}
            className="px-3 py-1 border rounded bg-blue-100"
            disabled={loading}
          >
            {loading ? '저장중...' : '저장'}
          </button>
        </div>
      </div>
    </div>
  );
}