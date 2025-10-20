package com.backend.domain.review.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.repository.PlaceRepository;
import com.backend.domain.review.dto.ReviewRequestDto;
import com.backend.domain.review.dto.ReviewResponseDto;
import com.backend.domain.review.entity.Review;
import com.backend.domain.review.repository.ReviewRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;


    //리뷰 생성 메서드
    @Transactional
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto){

        Member member = memberRepository.findById(reviewRequestDto.memberId()).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND)
        );
        Place place = placeRepository.findById(reviewRequestDto.placeId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_PLACE)
        );

        Optional<Review> check = reviewRepository.findByMemberIdAndPlaceId(member.getId(), place.getId());
        if(check.isPresent()){
            throw new BusinessException(ErrorCode.GIVEN_REVIEW);
        }
        Review review = new Review(place, member, reviewRequestDto.rating());
        review.onCreate();
        reviewRepository.save(review);

        return new ReviewResponseDto(review.getId(), review.getRating(), review.getModified_Date());
    }

    //리뷰 수정 메서드
    @Transactional
    public void modifyReview(long memberId, int modifyRating){
//        Review review = reviewRepository.findById(reviewId).orElseThrow(
//                () -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW)
//        );
//        Review review = getReviewEntity(reviewId);
        Review review = reviewRepository.findByMemberId(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW)
        );
        review.setRating(modifyRating);
        review.onUpdate();
    }

    //리뷰 삭제 메서드
    @Transactional
    public void deleteReview(long reviewId){
        Review review = getReviewEntity(reviewId);
//        Review review = reviewRepository.findById(reviewId).orElseThrow(
//                () -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW)
//        );
        reviewRepository.delete(review);
    }


    //내가 작성한 리뷰 조회
    public ReviewResponseDto getReview(long reviewId){
//        Review review = reviewRepository.findById(reviewId).orElseThrow(
//                () -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW));
        Review review = getReviewEntity(reviewId);

        ReviewResponseDto reviewResponseDto = new ReviewResponseDto(review.getId(), review.getRating(),review.getModified_Date());
        return reviewResponseDto;
    }
    //전체 리뷰 조회
    public List<ReviewResponseDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(review -> new ReviewResponseDto(review.getId(), review.getRating(), review.getModified_Date()))
                .toList();
    }

    //여행지의 전체 리뷰 조회
    public List<ReviewResponseDto> getReviewList(long placeId){
        List<Review> reviews = reviewRepository.findByPlaceId(placeId);
        return reviews.stream()
                .map(review -> new ReviewResponseDto(review.getId(), review.getRating(), review.getModified_Date()))
                .toList();
    }

    public List<ReviewResponseDto> recommendByPlace(long placeId){
        List<Review> recommandReviewLists = reviewRepository.findTop5ByPlaceIdOrderByRatingDesc(placeId);
        return recommandReviewLists.stream()
                .map(review -> new ReviewResponseDto(review.getId(), review.getRating(), review.getModified_Date()))
                .toList();

    }


    public Review getReviewEntity(long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW)
        );
    }
}
