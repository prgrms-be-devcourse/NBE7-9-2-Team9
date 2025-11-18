package com.backend.domain.review.service;

import com.backend.domain.category.entity.Category;
import com.backend.domain.category.repository.CategoryRepository;
import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.repository.PlaceRepository;
import com.backend.domain.recommend.entity.Recommend;
import com.backend.domain.recommend.repository.RecommendRepository;
import com.backend.domain.review.dto.RecommendResponse;
import com.backend.domain.review.dto.ReviewRequestDto;
import com.backend.domain.review.dto.ReviewResponseDto;
import com.backend.domain.review.entity.Review;
import com.backend.domain.review.repository.ReviewRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.response.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;
    private final CategoryRepository categoryRepository;
    private final RecommendRepository recommendRepository;

    //리뷰 생성 메서드
    @Transactional
    @CacheEvict(cacheNames = {"recommendTop5", "sortedPlaces"}, allEntries = true)
    public ReviewResponseDto createReview(ReviewRequestDto reviewRequestDto, Long memberId) {
        long placeId = reviewRequestDto.placeId();

        Member member = getMemberEntity(memberId);
        Place place = getPlaceEntity(placeId);

        Optional<Review> check = reviewRepository.findByMemberIdAndPlaceId(member.getId(), place.getId());
        if(check.isPresent()){
            throw new BusinessException(ErrorCode.GIVEN_REVIEW);
        }
        Review review = new Review(place, member, reviewRequestDto.rating(), reviewRequestDto.content());
        review.onCreate();
        reviewRepository.save(review);

        place.setRatingCount(place.getRatingCount() + 1);
        place.setRatingSum(place.getRatingSum() + reviewRequestDto.rating());

        placeRepository.save(place);
        updateRecommend(place);
        return new ReviewResponseDto(member.getMemberId(), review.getId(), review.getRating(), review.getContent(), review.getModifiedDate(), place.getCategory().getName(), place.getPlaceName(), place.getAddress(), place.getGu());
    }

    //리뷰 수정 메서드
    @Transactional
    @CacheEvict(cacheNames = {"recommendTop5", "sortedPlaces"}, allEntries = true)
    public void modifyReview(Long memberId, Long reviewId, int modifyRating, String content){
        Review review = (Review) reviewRepository.findByMemberIdAndId(memberId, reviewId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW)
        );

        int oldRating = review.getRating();

        review.setRating(modifyRating);
        review.setContent(content);
        review.onUpdate();
        reviewRepository.save(review);

        Place place = getPlaceEntity(review.getPlace().getId());
        place.setRatingSum(place.getRatingSum() - oldRating+ modifyRating);
        placeRepository.save(place);
        updateRecommend(place);

    }

    //리뷰 삭제 메서드
    @Transactional
    @CacheEvict(cacheNames = {"recommendTop5", "sortedPlaces"}, allEntries = true)      //리뷰가 지워지면 캐시도 초기화
    public void deleteReview(Long memberId, long reviewId){
        if(!validWithReviewId(memberId, reviewId)){
            throw new BusinessException(ErrorCode.ACCESS_DENIED);
        }
        Review review = getReviewEntity(reviewId);

        Place place = getPlaceEntity(review.getPlace().getId());
        place.setRatingCount(place.getRatingCount() - 1);
        place.setRatingSum(place.getRatingSum() - review.getRating());
        placeRepository.save(place);
        reviewRepository.delete(review);
        updateRecommend(place);
    }

    //내가 작성한 리뷰 조회
    public List<ReviewResponseDto> getMyReviews(Long memberId) {
        List<Review> myReviews = reviewRepository.findAllByMemberId(memberId);
        if(myReviews.isEmpty()){
            throw new BusinessException(ErrorCode.NOT_FOUND_REVIEW);
        }
        return myReviews.stream()
                .map(ReviewResponseDto::from)
                .toList();

    }

    //전체 리뷰 조회
    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewResponseDto::from)
                .toList();
    }

    //여행지의 전체 리뷰 조회
    public List<ReviewResponseDto> getReviewList(Long placeId) {
        return reviewRepository.findByPlaceId(placeId)
                .stream()
                .map(ReviewResponseDto::from)
                .toList();
    }


    public Review getReviewEntity(Long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_REVIEW)
        );
    }

    public Place getPlaceEntity(Long placeId){
        return placeRepository.findById(placeId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_PLACE)
        );
    }

    public Member getMemberEntity(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND)
        );
    }


    public boolean validWithReviewId(Long memberId, Long reviewId){
        Review review = getReviewEntity(reviewId);
        Member member = getMemberEntity(memberId);
        return review.getMember().getId().equals(member.getId());
    }


    public List<Map.Entry<Long, Double>> getAllPlacesAndCalculate(Category category) {
        Map<Long, Double> placeAverageRatings = new HashMap<>();
        List<Place> findAllPlaces = placeRepository.findByCategory_Name(category.getName());
        for(Place place : findAllPlaces){
            double averageRating = reviewRepository.findAverageRatingByPlaceId(place.getId());
            placeAverageRatings.put(place.getId(), averageRating);
        }
        List<Map.Entry<Long, Double>> sortedList = placeAverageRatings.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed()) // 값 기준 내림차순
                .toList();
        return sortedList;
    }


    public double getWeightByBayesian (double averageRating, double reviewCount, double globalAverageRating){
            double threshold = 10; // 신뢰도 임계값 설정
            double weight = (reviewCount / (reviewCount + threshold)) * averageRating + (threshold / (reviewCount + threshold)) * globalAverageRating;
            return weight;
    }


    @Cacheable(cacheNames = "recommendTop5", key = "#categoryName")
    public List<RecommendResponse> recommendPlace(String categoryName) {

        List<Recommend> recommend = recommendRepository.findTop5ByPlaceCategoryNameOrderByBayesianRatingDesc(categoryName);

        return recommend.stream()
                .map(recommendPlace -> RecommendResponse.from(recommendPlace.getPlace(), recommendPlace.getBayesianRating()))
                .toList();
    }

    @Cacheable(cacheNames = "sortedPlaces", key = "#categoryName")
    public List<RecommendResponse> sortPlaces(String categoryName) {

        List<Recommend> recommend = recommendRepository.findByPlaceCategoryNameOrderByBayesianRatingDesc(categoryName);

        return recommend.stream()
                .map(recommendPlace -> RecommendResponse.from(recommendPlace.getPlace(), recommendPlace.getBayesianRating()))
                .toList();
    }


    public double getGlobalAverageRating(){         //인덱스를 이용해서 global average rating 구하기
        return reviewRepository.findGlobalAverageRating();
    }

    public double getAverageRating(Long placeId){           // 인덱스를 이용해서 특정장소의 average rating 구하기
        return reviewRepository.findAverageRating(placeId);
    }

    public void updateRecommend(Place place){
        double averageRating = getAverageRating(place.getId());
        long reviewCount = place.getRatingCount();
        double globalAverageRating = getGlobalAverageRating();
        double weight = getWeightByBayesian(averageRating, (double)reviewCount, globalAverageRating);
        Recommend recommend = recommendRepository.findByPlaceId(place.getId()).orElseGet(() -> Recommend.create(place, averageRating, reviewCount, weight));
        recommend.updateRecommend(averageRating, reviewCount, weight);
        recommendRepository.save(recommend);
        place.setRatingAvg(averageRating);
        place.setRatingCount((int)reviewCount);
        placeRepository.save(place);

    }

}