package com.backend.domain.review.repository;

import com.backend.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    // 특정 회원이 특정 장소에 남긴 리뷰가 있는지 확인하는 메서드
    Optional<Review> findByMemberIdAndPlaceId(Long memberId, Long placeId);
    List<Review> findByPlaceId(Long placeId);
    Optional<Review> findByMemberId(Long memberId);
    List findTop5ByPlaceIdOrderByRatingDesc(long placeId);

    @Query(
            "SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.place.id = :placeId"       // 장소 ID에 해당하는 리뷰들의 평균 평점을 계산
    )
    double findAverageRatingByPlaceId(@Param("placeId") long placeId);

}
