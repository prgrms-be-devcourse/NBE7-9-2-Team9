package com.backend.domain.recommend.entity;


import com.backend.domain.place.entity.Place;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double averageRating;      //전체 평균 평점

    @Column(nullable = false)
    private long reviewCount;        //총 리뷰 개수

    @Column(nullable = false)
    private double bayesianRating;    //베이지안 가중치 점수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public static Recommend create(Place place, double averageRating, long reviewCount, double bayesianRating) {
        Recommend recommend = new Recommend();
        recommend.place = place;
        recommend.averageRating = averageRating;
        recommend.reviewCount = reviewCount;
        recommend.bayesianRating = bayesianRating;
        recommend.updatedAt = LocalDateTime.now();
        return recommend;

    }

    public void updateRecommend(double averageRating, long reviewCount, double bayesianRating) {
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.bayesianRating = bayesianRating;
        this.updatedAt = LocalDateTime.now();
    }

}
