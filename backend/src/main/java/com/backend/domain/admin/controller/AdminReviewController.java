package com.backend.domain.admin.controller;

import com.backend.domain.admin.service.AdminReviewService;
import com.backend.domain.review.dto.ReviewResponseDto;
import com.backend.global.reponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    /** 전체 리뷰 조회 */
    @GetMapping
    public ApiResponse<List<ReviewResponseDto>> getAllReviews() {
        return ApiResponse.success(adminReviewService.getAllReviews());
    }

    /** 장소별 리뷰 조회 */
    @GetMapping("/place/{placeId}")
    public ApiResponse<List<ReviewResponseDto>> getReviewsByPlace(@PathVariable Long placeId) {
        return ApiResponse.success(adminReviewService.getReviewsByPlace(placeId));
    }

    /** 리뷰 단건 조회 */
    @GetMapping("/{reviewId}")
    public ApiResponse<ReviewResponseDto> getReview(@PathVariable Long reviewId) {
        return ApiResponse.success(adminReviewService.getReview(reviewId));
    }

    /** 리뷰 강제 삭제 */
    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> deleteReview(@PathVariable Long reviewId) {
        adminReviewService.forceDeleteReview(reviewId);
        return ApiResponse.success(null, "리뷰 삭제 완료");
    }

    /** 장소별 상위 리뷰 조회 */
    @GetMapping("/top/{placeId}")
    public ApiResponse<List<ReviewResponseDto>> getTopReviews(@PathVariable Long placeId) {
        return ApiResponse.success(adminReviewService.getTopReviews(placeId));
    }
}