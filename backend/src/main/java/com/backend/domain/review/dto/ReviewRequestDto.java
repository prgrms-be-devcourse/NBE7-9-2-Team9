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
}
