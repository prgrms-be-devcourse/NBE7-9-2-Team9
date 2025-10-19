package com.backend.domain.review.dto;

import java.time.LocalDateTime;

public record ReviewResponseDto(

        long reviewId,
        int rating,
        LocalDateTime modify_date

) {



}
