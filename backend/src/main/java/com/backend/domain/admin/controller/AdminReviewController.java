package com.backend.domain.admin.controller;


import com.backend.domain.admin.service.AdminReviewService;
import com.backend.domain.place.dto.ResponsePlaceDto;
import com.backend.domain.review.dto.ReviewResponseDto;
import com.backend.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final AdminReviewService adminReviewService;

    @GetMapping()
    public ApiResponse<List<ReviewResponseDto>> getAllReviews() {
        return ApiResponse.success(adminReviewService.getAllReviews());
    }


    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> deleteReview(Long reviewId) {
        adminReviewService.deleteReview(reviewId);
        return ApiResponse.success(null, "리뷰가 성공적으로 삭제되었습니다.");
    }

}
