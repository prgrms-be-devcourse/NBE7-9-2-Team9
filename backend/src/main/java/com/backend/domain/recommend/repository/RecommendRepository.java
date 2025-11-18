package com.backend.domain.recommend.repository;

import com.backend.domain.recommend.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {


    Optional<Recommend> findByPlaceId(Long placeId);

    List<Recommend> findByPlaceCategoryNameOrderByBayesianRatingDesc(String categoryName);      // 카테고리별 추천 장소 조회

    List<Recommend> findTop5ByPlaceCategoryNameOrderByBayesianRatingDesc(String categoryName);  // 카테고리별 상위 5개 추천 장소 조회

//    Recommend findByPlaceId(Long placeId);
}
