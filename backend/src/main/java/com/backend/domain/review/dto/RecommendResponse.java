package com.backend.domain.review.dto;

import com.backend.domain.place.entity.Place;

public record RecommendResponse(
        Long id,
String placeName,
String address,
String gu,
String category,
String description,
int reviewCnt,
double averageRating
) {
public static RecommendResponse from(Place place, double averageRating) {
    return new RecommendResponse(
            place.getId(),
            place.getPlaceName(),
            place.getAddress(),
            place.getGu(),
            place.getCategory().getName(),
            place.getDescription(),
            place.getRatingCount(),
            averageRating
    );
}
}
