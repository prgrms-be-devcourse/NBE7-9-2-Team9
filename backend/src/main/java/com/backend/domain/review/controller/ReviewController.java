package com.backend.domain.review.controller;


import com.backend.domain.auth.service.AuthService;
import com.backend.domain.review.dto.RecommendResponse;
import com.backend.domain.review.dto.ReviewRequestDto;
import com.backend.domain.review.dto.ReviewResponseDto;
import com.backend.domain.review.service.ReviewService;
import com.backend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthService authService;

    //리뷰 등록
    @PostMapping("/add")
    public ApiResponse<ReviewResponseDto> createReview(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String accessToken,
            @RequestBody ReviewRequestDto reviewRequestDto
    ) {
        Long memberId = authService.getMemberId(accessToken);
        ReviewResponseDto createdReview = reviewService.createReview(reviewRequestDto, memberId);
        return ApiResponse.created(createdReview);
    }

    //리뷰 수정
    @PatchMapping("/modify/{reviewId}")
    public ApiResponse<Void> modifyReview(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String accessToken,
            @PathVariable long reviewId, @RequestParam int modifyRating, @RequestParam String modifyContent) {
        Long authMemberId = authService.getMemberId(accessToken);
        reviewService.modifyReview(authMemberId, reviewId, modifyRating, modifyContent);
        return ApiResponse.success();
    }

    //리뷰 삭제
    @DeleteMapping("/delete/{reviewId}")
    public ApiResponse<Void> deleteReview(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String accessToken,
            @PathVariable long reviewId) {
        Long memberId = authService.getMemberId(accessToken);
        reviewService.deleteReview(memberId, reviewId);
        return ApiResponse.success();
    }

    // 내가 작성한 리뷰 조회
    @GetMapping("/myReview") // 꼭 reviewId가 필요한가?
    public ApiResponse<List<ReviewResponseDto>> getMyReview(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String accessToken) {
        Long memberId = authService.getMemberId(accessToken);
        List<ReviewResponseDto> response = reviewService.getMyReviews(memberId);
//        ReviewResponseDto response = reviewService.getReview(memberId, reviewId);
        return ApiResponse.success(response);
    }

    // 특정 여행지의 리뷰 조회
    @GetMapping("/list/{placeId}")
    public ApiResponse<List<ReviewResponseDto>> getPlaceReview(@PathVariable long placeId) {
        List<ReviewResponseDto> reviews = reviewService.getReviewList(placeId);
        return ApiResponse.success(reviews);
    }

    // 전체 리뷰 조회
    @GetMapping("/lists")
    public ApiResponse<List<ReviewResponseDto>> getAllReview() {
        List<ReviewResponseDto> reviews = reviewService.getAllReviews();
        return ApiResponse.success(reviews);
    }


    //카테고리별 추천
    @GetMapping("/recommend/{category}")
    public ApiResponse<List<RecommendResponse>> sortAllNightspotReviews(
            @PathVariable String category
    ){
        List<RecommendResponse> recommendedPlaces = reviewService.recommendPlace(category);
        return ApiResponse.success(recommendedPlaces);
    }

    //카테고리별 내림차순 정렬
    @GetMapping("/recommend/sort/{category}")
    public ApiResponse<List<RecommendResponse>> sortRecommendedPlacesByCategory(
            @PathVariable String category
    ) {
        List<RecommendResponse> recommendedPlaces = reviewService.sortPlaces(category);
        return ApiResponse.success(recommendedPlaces);
    }
}
