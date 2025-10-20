import React, { useState, useEffect } from "react";
import Input from "../atoms/Input";
import Textarea from "../atoms/Textarea";
import Button from "../atoms/Button";
import Card from "../atoms/Card";
import "./PlaceForm.css";

const PlaceForm = ({
  place = null,
  categoryId,
  onSubmit,
  onCancel,
  isEditing = false,
}) => {
  const [formData, setFormData] = useState({
    placeName: "",
    address: "",
    gu: "",
    description: "",
    categoryId: categoryId || "",
  });

  const [errors, setErrors] = useState({});

  useEffect(() => {
    if (place && isEditing) {
      setFormData({
        placeName: place.placeName || "",
        address: place.address || "",
        gu: place.gu || "",
        description: place.description || "",
        categoryId: place.category?.id || categoryId,
      });
    }
  }, [place, isEditing, categoryId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));

    // 에러 메시지 제거
    if (errors[name]) {
      setErrors((prev) => ({
        ...prev,
        [name]: "",
      }));
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.placeName.trim()) {
      newErrors.placeName = "여행지 이름을 입력해주세요.";
    }

    if (!formData.address.trim()) {
      newErrors.address = "주소를 입력해주세요.";
    }

    if (!formData.gu.trim()) {
      newErrors.gu = "구를 입력해주세요.";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (validateForm()) {
      onSubmit(formData);
    }
  };

  return (
    <Card className="place-form-container">
      <div className="form-header">
        <h2>{isEditing ? "여행지 수정" : "여행지 등록"}</h2>
        <p>
          {isEditing
            ? "여행지 정보를 수정하세요."
            : "새로운 여행지를 등록하세요."}
        </p>
      </div>

      <form onSubmit={handleSubmit} className="place-form">
        <Input
          name="placeName"
          label="여행지 이름"
          placeholder="여행지 이름을 입력하세요"
          value={formData.placeName}
          onChange={handleChange}
          required
          error={errors.placeName}
        />

        <Input
          name="address"
          label="주소"
          placeholder="주소를 입력하세요"
          value={formData.address}
          onChange={handleChange}
          required
          error={errors.address}
        />

        <Input
          name="gu"
          label="구"
          placeholder="구를 입력하세요 (예: 강남구, 서초구)"
          value={formData.gu}
          onChange={handleChange}
          required
          error={errors.gu}
        />

        <Textarea
          name="description"
          label="설명"
          placeholder="여행지에 대한 설명을 입력하세요"
          value={formData.description}
          onChange={handleChange}
          rows={5}
        />

        <div className="form-actions">
          <Button type="button" variant="secondary" onClick={onCancel}>
            취소
          </Button>
          <Button type="submit" variant="primary">
            {isEditing ? "수정하기" : "등록하기"}
          </Button>
        </div>
      </form>
    </Card>
  );
};

export default PlaceForm;
