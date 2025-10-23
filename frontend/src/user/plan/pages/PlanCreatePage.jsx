import React, { useState } from 'react';

export default function PlanCreateForm() {
  const [formData, setFormData] = useState({
    title: '',
    content: '',
    placeId: '',
    startDate: '',
    endDate: ''
  });
  
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  // 오늘 날짜와 10년 후 날짜 계산
  const today = new Date();
  today.setHours(0, 0, 0, 1);
  const minDate = today.toISOString().split('T')[0];
  
  const maxDate = new Date();
  maxDate.setFullYear(maxDate.getFullYear() + 10);
  const maxDateStr = maxDate.toISOString().split('T')[0];

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
    
    if (errors[name]) {
      setErrors(prev => ({
        ...prev,
        [name]: ''
      }));
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.title.trim()) {
      newErrors.title = '계획 제목을 입력해주세요.';
    }


    if (!formData.startDate) {
      newErrors.startDate = '시작 날짜를 선택해주세요.';
    }

    if (!formData.endDate) {
      newErrors.endDate = '종료 날짜를 선택해주세요.';
    }

    if (formData.startDate && formData.endDate) {
      const start = new Date(formData.startDate);
      const end = new Date(formData.endDate);
      
      if (start > end) {
        newErrors.endDate = '종료 날짜는 시작 날짜 이후여야 합니다.';
      }
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    setIsSubmitting(true);

    try {
      const startDateTime = `${formData.startDate}T00:00:00`;
      const endDateTime = `${formData.endDate}T23:59:59`;

      const requestBody = {
        title: formData.title,
        content: formData.content,
        startDate: startDateTime,
        endDate: endDateTime
      };

      const response = await fetch('http://localhost:8080/api/plan/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(requestBody),
        credentials: 'include'
      });

      if (response.status === 200) {
        alert('작성 성공');
        window.location.href = 'http://localhost:3000/user/plan/list';
      } else {
        alert('오류가 발생했습니다');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('오류가 발생했습니다');
    } finally {
      setIsSubmitting(false);
    }
  };

  const styles = {
    container: {
      minHeight: '100vh',
      background: 'linear-gradient(to bottom right, #eff6ff, #e0e7ff)',
      padding: '48px 16px',
    },
    wrapper: {
      maxWidth: '672px',
      margin: '0 auto',
    },
    card: {
      backgroundColor: 'white',
      borderRadius: '16px',
      boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)',
      padding: '32px',
    },
    header: {
      marginBottom: '32px',
    },
    title: {
      fontSize: '30px',
      fontWeight: 'bold',
      color: '#1f2937',
      marginBottom: '8px',
    },
    subtitle: {
      color: '#4b5563',
    },
    formGroup: {
      marginBottom: '24px',
    },
    label: {
      display: 'block',
      fontSize: '14px',
      fontWeight: '600',
      color: '#374151',
      marginBottom: '8px',
    },
    input: {
      width: '100%',
      padding: '12px 16px',
      border: '1px solid #d1d5db',
      borderRadius: '8px',
      fontSize: '16px',
      transition: 'all 0.2s',
      boxSizing: 'border-box',
    },
    inputFocus: {
      outline: 'none',
      borderColor: '#3b82f6',
      boxShadow: '0 0 0 3px rgba(59, 130, 246, 0.1)',
    },
    textarea: {
      width: '100%',
      padding: '12px 16px',
      border: '1px solid #d1d5db',
      borderRadius: '8px',
      fontSize: '16px',
      resize: 'none',
      fontFamily: 'inherit',
      transition: 'all 0.2s',
      boxSizing: 'border-box',
    },
    error: {
      marginTop: '4px',
      fontSize: '14px',
      color: '#dc2626',
    },
    hint: {
      marginTop: '4px',
      fontSize: '12px',
      color: '#6b7280',
    },
    dateGrid: {
      display: 'grid',
      gridTemplateColumns: '1fr',
      gap: '16px',
    },
    buttonGroup: {
      display: 'flex',
      gap: '12px',
      paddingTop: '16px',
    },
    button: {
      flex: 1,
      padding: '12px 24px',
      borderRadius: '8px',
      fontSize: '16px',
      fontWeight: '600',
      cursor: 'pointer',
      transition: 'all 0.2s',
      border: 'none',
    },
    cancelButton: {
      backgroundColor: 'white',
      color: '#374151',
      border: '1px solid #d1d5db',
    },
    submitButton: {
      backgroundColor: '#2563eb',
      color: 'white',
    },
    submitButtonDisabled: {
      backgroundColor: '#9ca3af',
      cursor: 'not-allowed',
    },
  };

  return (
    <div style={styles.container}>
      <div style={styles.wrapper}>
        <div style={styles.card}>
          <div style={styles.header}>
            <h1 style={styles.title}>여행 계획 작성</h1>
            <p style={styles.subtitle}>새로운 여행 계획을 만들어보세요</p>
          </div>

          <div>
            {/* 계획 제목 */}
            <div style={styles.formGroup}>
              <label style={styles.label}>
                📝 계획 제목
              </label>
              <input
                type="text"
                name="title"
                value={formData.title}
                onChange={handleChange}
                placeholder="예: 제주도 가족 여행"
                style={styles.input}
                onFocus={(e) => Object.assign(e.target.style, styles.inputFocus)}
                onBlur={(e) => {
                  e.target.style.borderColor = '#d1d5db';
                  e.target.style.boxShadow = 'none';
                }}
              />
              {errors.title && (
                <p style={styles.error}>{errors.title}</p>
              )}
            </div>
  

            {/* 날짜 선택 */}
            <div style={styles.dateGrid}>
              <div style={styles.formGroup}>
                <label style={styles.label}>
                  📅 시작 날짜
                </label>
                <input
                  type="date"
                  name="startDate"
                  value={formData.startDate}
                  onChange={handleChange}
                  min={minDate}
                  max={maxDateStr}
                  style={styles.input}
                  onFocus={(e) => Object.assign(e.target.style, styles.inputFocus)}
                  onBlur={(e) => {
                    e.target.style.borderColor = '#d1d5db';
                    e.target.style.boxShadow = 'none';
                  }}
                />
                {errors.startDate && (
                  <p style={styles.error}>{errors.startDate}</p>
                )}
              </div>

              <div style={styles.formGroup}>
                <label style={styles.label}>
                  ⏰ 종료 날짜
                </label>
                <input
                  type="date"
                  name="endDate"
                  value={formData.endDate}
                  onChange={handleChange}
                  min={formData.startDate || minDate}
                  max={maxDateStr}
                  style={styles.input}
                  onFocus={(e) => Object.assign(e.target.style, styles.inputFocus)}
                  onBlur={(e) => {
                    e.target.style.borderColor = '#d1d5db';
                    e.target.style.boxShadow = 'none';
                  }}
                />
                {errors.endDate && (
                  <p style={styles.error}>{errors.endDate}</p>
                )}
              </div>
            </div>

            <p style={styles.hint}>
              ※ 오늘 이전 날짜와 10년 이후 날짜는 선택할 수 없습니다
            </p>

            {/* 내용 */}
            <div style={styles.formGroup}>
              <label style={styles.label}>
                📄 내용 (선택사항)
              </label>
              <textarea
                name="content"
                value={formData.content}
                onChange={handleChange}
                placeholder="여행 계획에 대한 설명을 입력해주세요"
                rows="5"
                style={styles.textarea}
                onFocus={(e) => Object.assign(e.target.style, styles.inputFocus)}
                onBlur={(e) => {
                  e.target.style.borderColor = '#d1d5db';
                  e.target.style.boxShadow = 'none';
                }}
              />
            </div>

            {/* 제출 버튼 */}
            <div style={styles.buttonGroup}>
              <button
                type="button"
                onClick={() => window.history.back()}
                style={{...styles.button, ...styles.cancelButton}}
                onMouseEnter={(e) => e.target.style.backgroundColor = '#f9fafb'}
                onMouseLeave={(e) => e.target.style.backgroundColor = 'white'}
              >
                취소
              </button>
              <button
                type="button"
                onClick={handleSubmit}
                disabled={isSubmitting}
                style={{
                  ...styles.button,
                  ...styles.submitButton,
                  ...(isSubmitting ? styles.submitButtonDisabled : {})
                }}
                onMouseEnter={(e) => {
                  if (!isSubmitting) e.target.style.backgroundColor = '#1d4ed8';
                }}
                onMouseLeave={(e) => {
                  if (!isSubmitting) e.target.style.backgroundColor = '#2563eb';
                }}
              >
                {isSubmitting ? '작성 중...' : '계획 작성'}
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <style>{`
        @media (min-width: 768px) {
          .date-grid {
            grid-template-columns: 1fr 1fr;
          }
        }
      `}</style>
    </div>
  );
}