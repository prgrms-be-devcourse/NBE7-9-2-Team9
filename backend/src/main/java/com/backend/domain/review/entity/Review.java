package com.backend.domain.review.entity;

import com.backend.domain.member.entity.Member;
import com.backend.domain.place.entity.Place;
import com.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="review")
@NoArgsConstructor
@Getter
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;


//    @Column(nullable = false, name = "created_date")
//    private LocalDateTime createdDate;
//
//    @Column(nullable = false ,name = "modified_date")
//    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

//    public void onCreate() {
//        this.createdDate = LocalDateTime.now();
//        this.modifiedDate = LocalDateTime.now();
//    }
//
//    public void onUpdate() {
//        this.modifiedDate = LocalDateTime.now();
//    }


    public Review(Place place, Member member, int rating) {
        this.place = place;
        this.member = member;
        this.rating = rating;
//        this.createdDate = LocalDateTime.now();
    }

    public int setRating(int rating) {
        this.rating = rating;
        return rating;
    }

}
