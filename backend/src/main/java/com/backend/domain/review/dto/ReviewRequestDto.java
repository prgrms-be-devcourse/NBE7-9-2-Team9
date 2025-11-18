package com.backend.domain.review.dto;

public record ReviewRequestDto(
        Long memberId,
        Long placeId,
        int rating,
        String content,
        String Category,
        String placeName,
        String address,
        String gu
) {
    // ⭐ 테스트용 생성자
    public ReviewRequestDto(Long placeId, int rating, String content) {
        this(null, placeId, rating, content, null, null, null, null);
    }
}