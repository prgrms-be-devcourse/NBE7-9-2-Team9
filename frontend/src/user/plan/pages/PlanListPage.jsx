import React, { useState, useEffect } from 'react';

// 여행 계획 목록 컴포넌트
function PlanListPage({ onSelectPlan }) {
  const [plans, setPlans] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchPlans();
  }, []);

  const fetchPlans = async () => {
    try {
      setLoading(true);
      const response = await fetch('http://localhost:8080/api/plan/list');
      
      if (!response.ok) {
        throw new Error('계획 목록을 불러오는데 실패했습니다.');
      }
      
      const result = await response.json();
      setPlans(result.data || []);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const formatDateTime = (dateTime) => {
    const date = new Date(dateTime);
    return date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  const truncateContent = (content, maxLength = 30) => {
    if (!content) return '';
    if (content.length <= maxLength) return content;
    return content.substring(0, maxLength) + '...';
  };

  const handleHomeClick = () => {
    window.location.href = 'http://localhost:3000/user/';
  };

  if (loading) {
    return (
      <div style={styles.loadingContainer}>
        <div style={styles.spinner}></div>
        <p>로딩 중...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div style={styles.loadingContainer}>
        <div style={styles.errorBox}>
          <p style={styles.errorText}>{error}</p>
          <button onClick={fetchPlans} style={styles.retryButton}>
            다시 시도
          </button>
        </div>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <div style={styles.content}>
        <div style={styles.header}>
          <button onClick={handleHomeClick} style={styles.homeButton}>
            🏠 여행 홈
          </button>
          
          <h1 style={styles.title}>여행 계획 목록</h1>
          <p style={styles.subtitle}>총 {plans.length}개의 여행 계획</p>
        </div>

        {plans.length === 0 ? (
          <div style={styles.emptyBox}>
            <p style={styles.emptyText}>아직 등록된 여행 계획이 없습니다.</p>
          </div>
        ) : (
          <div style={styles.grid}>
            {plans.map((plan) => (
              <div
                key={plan.id}
                onClick={() => onSelectPlan(plan.id)}
                style={styles.card}
              >
                <div style={styles.cardContent}>
                  <div style={styles.cardSection}>
                    <h3 style={styles.cardLabel}>제목</h3>
                    <p style={styles.cardTitle}>{plan.title}</p>
                  </div>

                  <div style={styles.cardSection}>
                    <h3 style={styles.cardLabel}>내용</h3>
                    <p style={styles.cardText}>{truncateContent(plan.content, 30)}</p>
                  </div>

                  <div style={styles.cardSection}>
                    <h3 style={styles.cardLabel}>기간</h3>
                    <p style={styles.cardDate}>
                      {formatDateTime(plan.startDate)} ~ {formatDateTime(plan.endDate)}
                    </p>
                  </div>
                </div>
                <div style={styles.cardBorder}></div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

// 여행 계획 상세 컴포넌트
function PlanDetailPage({ planId, onBack }) {
  const [plan, setPlan] = useState(null);
  const [planDetails, setPlanDetails] = useState([]);
  const [isEditing, setIsEditing] = useState(false);
  const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
  const [showAddForm, setShowAddForm] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editingDetailId, setEditingDetailId] = useState(null);
  const [editingDetailData, setEditingDetailData] = useState({});
  
  const [editData, setEditData] = useState({
    title: '',
    content: '',
    startDate: '',
    endDate: ''
  });

  const [newDetail, setNewDetail] = useState({
    placeId: '',
    startTime: '',
    endTime: '',
    title: '',
    content: ''
  });

  useEffect(() => {
    fetchPlanDetail();
    fetchPlanDetailsList();
  }, [planId]);

  const fetchPlanDetail = async () => {
    try {
      setLoading(true);
      const response = await fetch(`http://localhost:8080/api/plan/${planId}`);
      
      if (!response.ok) {
        throw new Error('계획 상세를 불러오는데 실패했습니다.');
      }
      
      const result = await response.json();
      const data = result.data;
      setPlan(data);
      setEditData({
        title: data.title,
        content: data.content,
        startDate: data.startDate,
        endDate: data.endDate
      });
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const fetchPlanDetailsList = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/plan/detail/${planId}/list`);
      
      if (!response.ok) {
        throw new Error('상세 목록을 불러오는데 실패했습니다.');
      }
      
      const result = await response.json();
      setPlanDetails(result.data || []);
    } catch (err) {
      console.error('상세 목록 불러오기 실패:', err);
    }
  };

  const handleUpdate = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/plan/update/${planId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(editData)
      });

      if (!response.ok) {
        throw new Error('수정에 실패했습니다.');
      }

      const result = await response.json();
      setPlan(result.data);
      setIsEditing(false);
      alert('수정이 완료되었습니다.');
    } catch (err) {
      alert(err.message);
    }
  };

  const handleDelete = async () => {
    try {
      const response = await fetch(`http://localhost:8080/api/plan/delete/${planId}`, {
        method: 'DELETE'
      });

      if (!response.ok) {
        throw new Error('삭제에 실패했습니다.');
      }

      alert('삭제가 완료되었습니다.');
      onBack();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleAddDetail = async () => {
    try {
      const requestBody = {
        planId: planId,
        placeId: parseInt(newDetail.placeId),
        startTime: newDetail.startTime,
        endTime: newDetail.endTime,
        title: newDetail.title,
        content: newDetail.content
      };

      const response = await fetch('http://localhost:8080/api/plan/detail/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody)
      });

      if (!response.ok) {
        throw new Error('상세 일정 추가에 실패했습니다.');
      }

      alert('상세 일정이 추가되었습니다.');
      setShowAddForm(false);
      setNewDetail({
        placeId: '',
        startTime: '',
        endTime: '',
        title: '',
        content: ''
      });
      fetchPlanDetailsList();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleEditDetail = (detail) => {
    setEditingDetailId(detail.id);
    setEditingDetailData({
      placeId: detail.placeId,
      startTime: detail.startTime,
      endTime: detail.endTime,
      title: detail.title,
      content: detail.content
    });
  };

  const handleUpdateDetail = async (detailId) => {
    try {
      const requestBody = {
        planId: planId,
        placeId: parseInt(editingDetailData.placeId),
        startTime: editingDetailData.startTime,
        endTime: editingDetailData.endTime,
        title: editingDetailData.title,
        content: editingDetailData.content
      };

      const response = await fetch(`http://localhost:8080/api/plan/detail/update/${detailId}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody)
      });

      if (!response.ok) {
        throw new Error('상세 일정 수정에 실패했습니다.');
      }

      alert('상세 일정이 수정되었습니다.');
      setEditingDetailId(null);
      setEditingDetailData({});
      fetchPlanDetailsList();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleDeleteDetail = async (detailId) => {
    if (!window.confirm('이 상세 일정을 삭제하시겠습니까?')) {
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/api/plan/detail/delete/${detailId}`, {
        method: 'DELETE'
      });

      if (!response.ok) {
        throw new Error('상세 일정 삭제에 실패했습니다.');
      }

      alert('상세 일정이 삭제되었습니다.');
      fetchPlanDetailsList();
    } catch (err) {
      alert(err.message);
    }
  };

  const handleCancelEditDetail = () => {
    setEditingDetailId(null);
    setEditingDetailData({});
  };

  const isAddFormValid = () => {
    if (!newDetail.placeId || !newDetail.startTime || !newDetail.endTime || 
        !newDetail.title || !newDetail.content) {
      return false;
    }
    return isTimeInRange(newDetail.startTime) && isTimeInRange(newDetail.endTime);
  };

  const isTimeInRange = (time) => {
    if (!plan || !time) return true;
    const timeDate = new Date(time);
    const startDate = new Date(plan.startDate);
    const endDate = new Date(plan.endDate);
    return timeDate >= startDate && timeDate <= endDate;
  };

  const formatDateTime = (dateTime) => {
    const date = new Date(dateTime);
    return date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    });
  };

  const formatDetailDateTime = (dateTime) => {
    const date = new Date(dateTime);
    return date.toLocaleString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  if (loading) {
    return (
      <div style={styles.loadingContainer}>
        <div style={styles.spinner}></div>
        <p>로딩 중...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div style={styles.loadingContainer}>
        <div style={styles.errorBox}>
          <p style={styles.errorText}>{error}</p>
          <button onClick={onBack} style={styles.retryButton}>
            목록으로
          </button>
        </div>
      </div>
    );
  }

  return (
    <div style={styles.container}>
      <div style={styles.detailContent}>
        <button onClick={onBack} style={styles.backButton}>
          ← 목록으로
        </button>

        <div style={styles.detailBox}>
          <div style={styles.detailHeader}>
            <h1 style={styles.detailTitle}>여행 계획 상세</h1>
            {!isEditing ? (
              <div style={styles.buttonGroup}>
                <button
                  onClick={() => setIsEditing(true)}
                  style={styles.editButton}
                >
                  수정
                </button>
                <button
                  onClick={() => setShowDeleteConfirm(true)}
                  style={styles.deleteButton}
                >
                  삭제
                </button>
              </div>
            ) : (
              <div style={styles.buttonGroup}>
                <button
                  onClick={handleUpdate}
                  style={styles.saveButton}
                >
                  수정하기
                </button>
                <button
                  onClick={() => {
                    setIsEditing(false);
                    setEditData({
                      title: plan.title,
                      content: plan.content,
                      startDate: plan.startDate,
                      endDate: plan.endDate
                    });
                  }}
                  style={styles.cancelButton}
                >
                  취소
                </button>
              </div>
            )}
          </div>

          <div style={styles.formContainer}>
            <div style={styles.formGroup}>
              <h3 style={styles.formLabel}>제목</h3>
              {isEditing ? (
                <input
                  type="text"
                  value={editData.title}
                  onChange={(e) => setEditData({ ...editData, title: e.target.value })}
                  style={styles.input}
                />
              ) : (
                <p style={styles.formValue}>{plan.title}</p>
              )}
            </div>

            <div style={styles.formGroup}>
              <h3 style={styles.formLabel}>내용</h3>
              {isEditing ? (
                <textarea
                  value={editData.content}
                  onChange={(e) => setEditData({ ...editData, content: e.target.value })}
                  rows="6"
                  style={styles.textarea}
                />
              ) : (
                <p style={styles.formValueContent}>{plan.content}</p>
              )}
            </div>

            <div style={styles.formGroup}>
              <h3 style={styles.formLabel}>기간</h3>
              {isEditing ? (
                <div style={styles.dateRangeContainer}>
                  <input
                    type="datetime-local"
                    value={editData.startDate}
                    onChange={(e) => setEditData({ ...editData, startDate: e.target.value })}
                    style={styles.dateInput}
                  />
                  <span style={styles.dateSeparator}>~</span>
                  <input
                    type="datetime-local"
                    value={editData.endDate}
                    onChange={(e) => setEditData({ ...editData, endDate: e.target.value })}
                    style={styles.dateInput}
                  />
                </div>
              ) : (
                <p style={styles.formValue}>
                  {formatDateTime(plan.startDate)} ~ {formatDateTime(plan.endDate)}
                </p>
              )}
            </div>
          </div>
        </div>

        <div style={styles.detailBox}>
          <div style={styles.detailListHeader}>
            <h2 style={styles.sectionTitle}>여행 상세 일정</h2>
            <button
              onClick={() => setShowAddForm(!showAddForm)}
              style={styles.addButton}
            >
              {showAddForm ? '취소' : '계획 상세 추가하기'}
            </button>
          </div>

          {showAddForm && (
            <div style={styles.addFormContainer}>
              <h3 style={styles.addFormTitle}>새 상세 일정 추가</h3>
              <div style={styles.formGroup}>
                <label style={styles.formLabel}>장소 ID</label>
                <input
                  type="number"
                  value={newDetail.placeId}
                  onChange={(e) => setNewDetail({ ...newDetail, placeId: e.target.value })}
                  style={styles.input}
                  placeholder="장소 ID를 입력하세요"
                />
              </div>

              <div style={styles.formGroup}>
                <label style={styles.formLabel}>시작 시간</label>
                <input
                  type="datetime-local"
                  value={newDetail.startTime}
                  onChange={(e) => setNewDetail({ ...newDetail, startTime: e.target.value })}
                  style={styles.input}
                />
                {newDetail.startTime && !isTimeInRange(newDetail.startTime) && (
                  <p style={styles.warningText}>시작 시간은 계획 기간 내에 있어야 합니다.</p>
                )}
              </div>

              <div style={styles.formGroup}>
                <label style={styles.formLabel}>종료 시간</label>
                <input
                  type="datetime-local"
                  value={newDetail.endTime}
                  onChange={(e) => setNewDetail({ ...newDetail, endTime: e.target.value })}
                  style={styles.input}
                />
                {newDetail.endTime && !isTimeInRange(newDetail.endTime) && (
                  <p style={styles.warningText}>종료 시간은 계획 기간 내에 있어야 합니다.</p>
                )}
              </div>

              <div style={styles.formGroup}>
                <label style={styles.formLabel}>제목</label>
                <input
                  type="text"
                  value={newDetail.title}
                  onChange={(e) => setNewDetail({ ...newDetail, title: e.target.value })}
                  style={styles.input}
                  placeholder="제목을 입력하세요"
                />
              </div>

              <div style={styles.formGroup}>
                <label style={styles.formLabel}>내용</label>
                <textarea
                  value={newDetail.content}
                  onChange={(e) => setNewDetail({ ...newDetail, content: e.target.value })}
                  rows="4"
                  style={styles.textarea}
                  placeholder="내용을 입력하세요"
                />
              </div>

              <button
                onClick={handleAddDetail}
                disabled={!isAddFormValid()}
                style={{
                  ...styles.saveButton,
                  opacity: !isAddFormValid() ? 0.5 : 1,
                  cursor: !isAddFormValid() ? 'not-allowed' : 'pointer'
                }}
              >
                저장
              </button>
            </div>
          )}
          
          {planDetails.length === 0 ? (
            <div style={styles.emptyDetailBox}>
              <p style={styles.emptyText}>아직 등록된 상세 일정이 없습니다.</p>
            </div>
          ) : (
            <div style={styles.detailList}>
              {planDetails.map((detail) => (
                <div key={detail.id} style={styles.detailItem}>
                  {editingDetailId === detail.id ? (
                    // 수정 모드
                    <div>
                      <div style={styles.detailItemEditHeader}>
                        <h3 style={styles.detailItemTitle}>상세 일정 수정</h3>
                        <div style={styles.buttonGroup}>
                          <button
                            onClick={() => handleUpdateDetail(detail.id)}
                            style={styles.saveButton}
                          >
                            저장
                          </button>
                          <button
                            onClick={handleCancelEditDetail}
                            style={styles.cancelButton}
                          >
                            취소
                          </button>
                        </div>
                      </div>

                      <div style={styles.formContainer}>
                        <div style={styles.formGroup}>
                          <label style={styles.formLabel}>장소 ID</label>
                          <input
                            type="number"
                            value={editingDetailData.placeId}
                            onChange={(e) => setEditingDetailData({ ...editingDetailData, placeId: e.target.value })}
                            style={styles.input}
                          />
                        </div>

                        <div style={styles.formGroup}>
                          <label style={styles.formLabel}>시작 시간</label>
                          <input
                            type="datetime-local"
                            value={editingDetailData.startTime}
                            onChange={(e) => setEditingDetailData({ ...editingDetailData, startTime: e.target.value })}
                            style={styles.input}
                          />
                        </div>

                        <div style={styles.formGroup}>
                          <label style={styles.formLabel}>종료 시간</label>
                          <input
                            type="datetime-local"
                            value={editingDetailData.endTime}
                            onChange={(e) => setEditingDetailData({ ...editingDetailData, endTime: e.target.value })}
                            style={styles.input}
                          />
                        </div>

                        <div style={styles.formGroup}>
                          <label style={styles.formLabel}>제목</label>
                          <input
                            type="text"
                            value={editingDetailData.title}
                            onChange={(e) => setEditingDetailData({ ...editingDetailData, title: e.target.value })}
                            style={styles.input}
                          />
                        </div>

                        <div style={styles.formGroup}>
                          <label style={styles.formLabel}>내용</label>
                          <textarea
                            value={editingDetailData.content}
                            onChange={(e) => setEditingDetailData({ ...editingDetailData, content: e.target.value })}
                            rows="4"
                            style={styles.textarea}
                          />
                        </div>
                      </div>
                    </div>
                  ) : (
                    // 보기 모드
                    <div>
                      <div style={styles.detailItemHeader}>
                        <div>
                          <h3 style={styles.detailItemTitle}>{detail.title}</h3>
                          <div style={styles.detailItemPlace}>
                            📍 {detail.placeName}
                          </div>
                        </div>
                        <div style={styles.buttonGroup}>
                          <button
                            onClick={() => handleEditDetail(detail)}
                            style={styles.editSmallButton}
                          >
                            수정
                          </button>
                          <button
                            onClick={() => handleDeleteDetail(detail.id)}
                            style={styles.deleteSmallButton}
                          >
                            삭제
                          </button>
                        </div>
                      </div>

                      <p style={styles.detailItemContent}>{detail.content}</p>

                      <div style={styles.detailItemTime}>
                        🕐 {formatDetailDateTime(detail.startTime)} ~ {formatDetailDateTime(detail.endTime)}
                      </div>
                    </div>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      {showDeleteConfirm && (
        <div style={styles.modal}>
          <div style={styles.modalContent}>
            <h3 style={styles.modalTitle}>삭제 확인</h3>
            <p style={styles.modalText}>정말로 이 여행 계획을 삭제하시겠습니까?</p>
            <div style={styles.modalButtons}>
              <button onClick={handleDelete} style={styles.confirmDeleteButton}>
                삭제
              </button>
              <button onClick={() => setShowDeleteConfirm(false)} style={styles.cancelButton}>
                취소
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

// 메인 앱 컴포넌트
export default function App() {
  const [selectedPlanId, setSelectedPlanId] = useState(null);

  if (selectedPlanId) {
    return (
      <PlanDetailPage
        planId={selectedPlanId}
        onBack={() => setSelectedPlanId(null)}
      />
    );
  }

  return <PlanListPage onSelectPlan={setSelectedPlanId} />;
}

const styles = {
  container: {
    minHeight: '100vh',
    background: 'linear-gradient(to bottom right, #ebf8ff, #e0e7ff)',
    padding: '20px'
  },
  content: {
    maxWidth: '1200px',
    margin: '0 auto',
    padding: '20px'
  },
  detailContent: {
    maxWidth: '900px',
    margin: '0 auto',
    padding: '20px'
  },
  loadingContainer: {
    minHeight: '100vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    flexDirection: 'column',
    background: 'linear-gradient(to bottom right, #ebf8ff, #e0e7ff)'
  },
  spinner: {
    width: '48px',
    height: '48px',
    border: '4px solid #e5e7eb',
    borderTop: '4px solid #4f46e5',
    borderRadius: '50%',
    animation: 'spin 1s linear infinite',
    marginBottom: '16px'
  },
  header: {
    marginBottom: '32px'
  },
  homeButton: {
    marginBottom: '24px',
    padding: '12px 24px',
    backgroundColor: 'white',
    color: '#4f46e5',
    border: 'none',
    borderRadius: '8px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    fontSize: '16px',
    fontWeight: '600',
    cursor: 'pointer',
    transition: 'all 0.2s'
  },
  backButton: {
    marginBottom: '24px',
    padding: '12px 24px',
    backgroundColor: 'white',
    color: '#4f46e5',
    border: 'none',
    borderRadius: '8px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    fontSize: '16px',
    fontWeight: '600',
    cursor: 'pointer',
    transition: 'all 0.2s'
  },
  title: {
    fontSize: '36px',
    fontWeight: 'bold',
    color: '#1f2937',
    marginBottom: '8px'
  },
  subtitle: {
    fontSize: '16px',
    color: '#6b7280'
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))',
    gap: '24px'
  },
  card: {
    backgroundColor: 'white',
    borderRadius: '12px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    cursor: 'pointer',
    transition: 'all 0.3s',
    overflow: 'hidden'
  },
  cardContent: {
    padding: '24px'
  },
  cardSection: {
    marginBottom: '16px'
  },
  cardLabel: {
    fontSize: '12px',
    fontWeight: '600',
    color: '#6b7280',
    marginBottom: '4px'
  },
  cardTitle: {
    fontSize: '20px',
    fontWeight: 'bold',
    color: '#1f2937'
  },
  cardText: {
    fontSize: '14px',
    color: '#4b5563'
  },
  cardDate: {
    fontSize: '14px',
    color: '#374151'
  },
  cardBorder: {
    height: '4px',
    background: 'linear-gradient(to right, #6366f1, #3b82f6)'
  },
  emptyBox: {
    backgroundColor: 'white',
    borderRadius: '12px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    padding: '48px',
    textAlign: 'center'
  },
  emptyText: {
    fontSize: '18px',
    color: '#6b7280'
  },
  errorBox: {
    backgroundColor: 'white',
    padding: '32px',
    borderRadius: '8px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)'
  },
  errorText: {
    color: '#dc2626',
    fontSize: '14px'
  },
  warningText: {
    color: '#f59e0b',
    fontSize: '12px',
    marginTop: '4px'
  },
  retryButton: {
    marginTop: '16px',
    padding: '8px 16px',
    backgroundColor: '#4f46e5',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    cursor: 'pointer'
  },
  detailBox: {
    backgroundColor: 'white',
    borderRadius: '12px',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
    padding: '32px',
    marginBottom: '24px'
  },
  detailHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: '24px'
  },
  detailTitle: {
    fontSize: '30px',
    fontWeight: 'bold',
    color: '#1f2937'
  },
  buttonGroup: {
    display: 'flex',
    gap: '8px'
  },
  editButton: {
    padding: '10px 16px',
    backgroundColor: '#4f46e5',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    fontSize: '14px',
    cursor: 'pointer',
    transition: 'background-color 0.2s'
  },
  deleteButton: {
    padding: '10px 16px',
    backgroundColor: '#dc2626',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    fontSize: '14px',
    cursor: 'pointer',
    transition: 'background-color 0.2s'
  },
  saveButton: {
    padding: '10px 16px',
    backgroundColor: '#10b981',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    fontSize: '14px',
    cursor: 'pointer',
    transition: 'background-color 0.2s'
  },
  cancelButton: {
    padding: '10px 16px',
    backgroundColor: '#6b7280',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    fontSize: '14px',
    cursor: 'pointer',
    transition: 'background-color 0.2s'
  },
  formContainer: {
    display: 'flex',
    flexDirection: 'column',
    gap: '24px'
  },
  formGroup: {
    display: 'flex',
    flexDirection: 'column'
  },
  formLabel: {
    fontSize: '14px',
    fontWeight: '600',
    color: '#6b7280',
    marginBottom: '8px'
  },
  formValue: {
    fontSize: '20px',
    fontWeight: 'bold',
    color: '#1f2937'
  },
  formValueContent: {
    fontSize: '16px',
    color: '#374151',
    whiteSpace: 'pre-wrap'
  },
  input: {
    width: '100%',
    padding: '10px 16px',
    border: '1px solid #d1d5db',
    borderRadius: '8px',
    fontSize: '14px',
    boxSizing: 'border-box'
  },
  textarea: {
    width: '100%',
    padding: '10px 16px',
    border: '1px solid #d1d5db',
    borderRadius: '8px',
    fontSize: '14px',
    resize: 'vertical',
    fontFamily: 'inherit',
    boxSizing: 'border-box'
  },
  dateRangeContainer: {
    display: 'flex',
    gap: '16px',
    alignItems: 'center'
  },
  dateInput: {
    flex: 1,
    padding: '10px 16px',
    border: '1px solid #d1d5db',
    borderRadius: '8px',
    fontSize: '14px'
  },
  dateSeparator: {
    fontSize: '16px',
    color: '#6b7280'
  },
  detailListHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '24px'
  },
  sectionTitle: {
    fontSize: '24px',
    fontWeight: 'bold',
    color: '#1f2937'
  },
  addButton: {
    padding: '10px 20px',
    backgroundColor: '#4f46e5',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    fontSize: '14px',
    cursor: 'pointer',
    fontWeight: '600'
  },
  addFormContainer: {
    backgroundColor: '#f9fafb',
    padding: '24px',
    borderRadius: '8px',
    marginBottom: '24px'
  },
  addFormTitle: {
    fontSize: '18px',
    fontWeight: 'bold',
    color: '#1f2937',
    marginBottom: '16px'
  },
  emptyDetailBox: {
    textAlign: 'center',
    padding: '48px'
  },
  detailList: {
    display: 'flex',
    flexDirection: 'column',
    gap: '16px'
  },
  detailItem: {
    border: '1px solid #e5e7eb',
    borderRadius: '8px',
    padding: '24px',
    transition: 'box-shadow 0.2s'
  },
  detailItemHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: '16px'
  },
  detailItemEditHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '16px'
  },
  detailItemTitle: {
    fontSize: '18px',
    fontWeight: 'bold',
    color: '#1f2937',
    marginBottom: '4px'
  },
  detailItemPlace: {
    fontSize: '14px',
    color: '#6366f1'
  },
  detailItemContent: {
    fontSize: '14px',
    color: '#4b5563',
    marginBottom: '16px',
    whiteSpace: 'pre-wrap'
  },
  detailItemTime: {
    fontSize: '14px',
    color: '#6b7280',
    backgroundColor: '#f9fafb',
    padding: '12px',
    borderRadius: '8px'
  },
  modal: {
    position: 'fixed',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    zIndex: 1000
  },
  modalContent: {
    backgroundColor: 'white',
    padding: '32px',
    borderRadius: '12px',
    boxShadow: '0 20px 25px rgba(0, 0, 0, 0.15)',
    maxWidth: '400px',
    width: '90%'
  },
  modalTitle: {
    fontSize: '24px',
    fontWeight: 'bold',
    color: '#1f2937',
    marginBottom: '16px'
  },
  modalText: {
    fontSize: '16px',
    color: '#4b5563',
    marginBottom: '24px'
  },
  modalButtons: {
    display: 'flex',
    gap: '12px',
    justifyContent: 'flex-end'
  },
  confirmDeleteButton: {
    padding: '10px 20px',
    backgroundColor: '#dc2626',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    fontSize: '14px',
    cursor: 'pointer',
    fontWeight: '600'
  },
  editSmallButton: {
    padding: '6px 12px',
    backgroundColor: '#4f46e5',
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    fontSize: '12px',
    cursor: 'pointer',
    fontWeight: '500'
  },
  deleteSmallButton: {
    padding: '6px 12px',
    backgroundColor: '#dc2626',
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    fontSize: '12px',
    cursor: 'pointer',
    fontWeight: '500'
  }
};