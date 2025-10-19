package com.backend.domain.review.controller;


import com.backend.domain.review.dto.ReviewRequestDto;
import com.backend.domain.review.dto.ReviewResponseDto;
import com.backend.domain.review.service.ReviewService;
import com.backend.global.reponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //리뷰 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ApiResponse<Void> deleteReview(@PathVariable long reviewId) {
        reviewService.deleteReview(reviewId);
        return ApiResponse.success();
    }

    // ✅ 내가 작성한 리뷰 조회
    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewResponseDto> getMyReview(@PathVariable long reviewId) {
        ReviewResponseDto response = reviewService.getReview(reviewId);
        return ApiResponse.success(response);
    }

    // ✅ 특정 여행지의 리뷰 조회
    @GetMapping("/list/{placeId}")
    public ApiResponse<List<ReviewResponseDto>> getPlaceReview(@PathVariable long placeId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewList(placeId);
        return ApiResponse.success(reviews);
    }

    // ✅ 전체 리뷰 조회
    @GetMapping("/lists")
    public ApiResponse<List<ReviewResponseDto>> getAllReview() {
        List<ReviewResponseDto> reviews = reviewService.getAllReviews();
        return ApiResponse.success(reviews);
    }


}
