package com.backend.domain.place.dto;

public record RequestPlaceDto(
        String placeName,
        String address,
        String gu,
        Long categoryId,
        String description
) {}