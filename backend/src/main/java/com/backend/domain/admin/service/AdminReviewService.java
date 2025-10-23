package com.backend.domain.admin.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.repository.PlaceRepository;
import com.backend.domain.review.dto.ReviewResponseDto;
import com.backend.domain.review.entity.Review;
import com.backend.domain.review.repository.ReviewRepository;
import com.backend.domain.review.service.ReviewService;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    /** 전체 리뷰 조회 */
    public List<ReviewResponseDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    /*
    TODO: List<Review> findAllByMemberId(Long memberId); 필요
    // 특정 회원의 리뷰 조회
    public List<ReviewResponseDto> getReviewsByMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));

        List<Review> reviews = reviewRepository.findByMemberId(member.getId());
        return reviews.stream()
                .map(review -> new ReviewResponseDto(review.getId(), review.getRating(), review.getModified_Date()))
                .toList();
    }
    */

    /** 특정 장소의 리뷰 조회 */
    public List<ReviewResponseDto> getReviewsByPlace(Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        List<Review> reviews = reviewRepository.findByPlaceId(place.getId());
        return reviews.stream()
                .map(review -> new ReviewResponseDto(review.getId(), review.getRating(), review.getModified_Date()))
                .toList();
    }

    /** 리뷰 단건 조회 */
    public ReviewResponseDto getReview(Long reviewId) {
        return reviewService.getReview(reviewId);
    }

    /** 리뷰 강제 삭제 */
    public void forceDeleteReview(Long reviewId) {
        Review review = reviewService.getReviewEntity(reviewId);
        reviewRepository.delete(review);
    }

    /** 장소별 상위 리뷰 조회 */
    public List<ReviewResponseDto> getTopReviews(Long placeId) {
        return reviewService.recommendByPlace(placeId);
    }
}
