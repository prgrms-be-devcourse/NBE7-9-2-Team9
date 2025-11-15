package com.backend.domain.review.dto;

import com.backend.domain.category.entity.Category;
import com.backend.domain.member.entity.Member;
import com.backend.domain.place.entity.Place;
import com.backend.domain.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        String memberId,
        Long reviewId,
        int rating,
        String content,
        LocalDateTime modify_date,
        String category,
        String placeName,
        String address,
        String gu

) {

    public static ReviewResponseDto from(Review review) {
        Member member = review.getMember();
        Place place = review.getPlace();
        return new ReviewResponseDto(
                member.getMemberId(),
                review.getId(),
                review.getRating(),
                review.getContent(),
                review.getModifiedDate(),
                place.getCategory().getName(),
                place.getPlaceName(),
                place.getAddress(),
                place.getGu()
        );
    }

}
