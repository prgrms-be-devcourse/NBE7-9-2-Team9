package com.backend.domain.place.entity;

import com.backend.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String placeName;

    private String address;

    @Column(length = 50)
    private String gu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private long ratingSum = 0L;

    @Column(nullable = false)
    private int  ratingCount = 0;

    @Column(nullable = false)
    private double ratingAvg = 0.0;

    @Version private Long version; // 동시성 대비(낙관적 락)

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public void update(String placeName, String address, String gu, String description) {
        this.placeName = placeName;
        this.address = address;
        this.gu = gu;
        this.description = description;
    }

    // 새 리뷰 반영
    public void applyNewRating(int rating) {
        this.ratingSum += rating;
        this.ratingCount += 1;
        this.ratingAvg = (double) this.ratingSum / this.ratingCount;
    }

    // 기존 리뷰 점수 변경 반영
    public void applyRatingUpdate(int oldRating, int newRating) {
        this.ratingSum += (newRating - oldRating);
        // count 변화 없음
        this.ratingAvg = this.ratingCount == 0 ? 0.0 : (double) this.ratingSum / this.ratingCount;
    }

    // 리뷰 삭제 반영
    public void applyRatingRemoval(int rating) {
        this.ratingSum -= rating;
        this.ratingCount -= 1;
        this.ratingAvg = this.ratingCount == 0 ? 0.0 : (double) this.ratingSum / this.ratingCount;
    }
}