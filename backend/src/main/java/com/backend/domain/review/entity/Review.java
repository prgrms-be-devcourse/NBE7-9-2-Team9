package com.backend.domain.review.entity;

import com.backend.domain.member.entity.Member;
import com.backend.domain.place.entity.Place;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="review",
        indexes = {
                @Index(name = "idx_review_place_rating", columnList = "place_id, rating"),
                @Index(name = "ux_review_member_place", columnList = "member_id, place_id", unique = true)
        })
@NoArgsConstructor
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;


    @Column(nullable = false, name = "created_date")
    private LocalDateTime createdDate;

    @Column(nullable = false ,name = "modified_date")
    private LocalDateTime modifiedDate;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.modifiedDate = LocalDateTime.now();
    }

    public void onUpdate() {
        this.modifiedDate = LocalDateTime.now();
    }


    public Review(Place place, Member member, int rating, String content) {
        this.place = place;
        this.member = member;
        this.rating = rating;
        this.content = content;
        this.createdDate = LocalDateTime.now();
    }

    public int setRating(int rating) {
        this.rating = rating;
        return rating;
    }
    public String setContent(String content){
        this.content = content;
        return content;
    }

}
