package com.backend.domain.admin.service;


import com.backend.domain.place.repository.PlaceRepository;
import com.backend.domain.review.dto.ReviewResponseDto;
import com.backend.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    //전체 리뷰 조회

    @Transactional
    public List<ReviewResponseDto> getAllReviews(){
        return reviewRepository.findAll()
                .stream()
                .map(ReviewResponseDto::from)
                .toList();
    }

    //리뷰 삭제
    @Transactional
    public void deleteReview(Long reviewId){
        reviewRepository.deleteById(reviewId);
    }




}
