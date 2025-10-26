//package com.backend.domain.review.entity;
//
//import com.backend.domain.review.dto.RecommendResponse;
//import com.backend.domain.review.service.ReviewService;
//
//import java.util.List;
//import java.util.function.Function;
//
//public enum RecommendationType {
//
//    HOTEL("hotel", ReviewService::recommendHotel, ReviewService::sortAllHotelReviews),
//    RESTAURANT("restaurant", ReviewService::recommendRestaurant, ReviewService::sortAllRestaurantReviews),
//    NIGHTSPOT("nightspot", ReviewService::recommendNightSpot, ReviewService::sortAllNightSpotReviews);
//
//
//    private final String code;
//    private final Function<ReviewService, List<RecommendResponse>> recommendFunction;
//    private final Function<ReviewService, List<RecommendResponse>> sortFunction;
//
//    public RecommendationType codeOf(String code){
//        for(RecommendationType value : RecommendationType.values()){
//            if(value.code.equals(code)){
//                return value;
//            }
//        }
//        throw new RuntimeException();
//    }
//
//    public List<RecommendResponse> recommend(ReviewService reviewService){
//        return recommendFunction.apply(reviewService);
//    }
//    public List<RecommendResponse> sortAll(ReviewService reviewService){
//        return sortFunction.apply(reviewService);
//    }
//}
