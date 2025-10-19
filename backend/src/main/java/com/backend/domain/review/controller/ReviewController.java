package com.backend.domain.review.controller;


import com.backend.domain.review.dto.ReviewRequestDto;
import com.backend.domain.review.dto.ReviewResponseDto;
import com.backend.domain.review.service.ReviewService;
import com.backend.global.reponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 등록
    @PostMapping("/add")
    public ApiResponse<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto createdReview = reviewService.createReview(reviewRequestDto);
        return ApiResponse.created(createdReview);
    }

    //리뷰 수정
    @PatchMapping("/modify/{reviewId}")
    public ApiResponse<Void> modifyReview(@PathVariable long reviewId, @RequestParam int modifyRating) {
        reviewService.modifyReview(reviewId, modifyRating);
        return ApiResponse.success();
    }


}
