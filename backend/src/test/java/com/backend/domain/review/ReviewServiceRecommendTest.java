package com.backend.domain.review;

import com.backend.domain.category.entity.Category;
import com.backend.domain.category.repository.CategoryRepository;
import com.backend.domain.member.entity.Member;
import com.backend.domain.member.entity.Role;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.repository.PlaceRepository;
import com.backend.domain.recommend.entity.Recommend;
import com.backend.domain.recommend.repository.RecommendRepository;
import com.backend.domain.review.dto.ReviewRequestDto;
import com.backend.domain.review.dto.RecommendResponse;
import com.backend.domain.review.repository.ReviewRepository;

import com.backend.domain.review.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReviewServiceRecommendTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RecommendRepository recommendRepository;

    Member member;
    Category hotelCategory;
    Place place1, place2, place3;

    @BeforeEach
    void setUp() {
        // member
        member = memberRepository.save(
                Member.builder()
                        .memberId("user1")
                        .password("1111")
                        .email("test@test.com")
                        .nickname("유저1")
                        .role(Role.USER)
                        .build()
        );

        // category
        hotelCategory = categoryRepository.save(new Category("HOTEL"));

        // places
        place1 = placeRepository.save(
                Place.builder()
                        .placeName("호텔A")
                        .category(hotelCategory)
                        .address("주소A")
                        .gu("강남구")
                        .description("desc")
                        .build()
        );

        place2 = placeRepository.save(
                Place.builder()
                        .placeName("호텔B")
                        .category(hotelCategory)
                        .address("주소B")
                        .gu("강남구")
                        .description("desc")
                        .build()
        );

        place3 = placeRepository.save(
                Place.builder()
                        .placeName("호텔C")
                        .category(hotelCategory)
                        .address("주소C")
                        .gu("강남구")
                        .description("desc")
                        .build()
        );
    }

    // -------------------------------------------------

    @Test
    void 리뷰_생성하면_Recommend_생성된다() {
        // when
        reviewService.createReview(new ReviewRequestDto(
                place1.getId(), 5, "좋아요"
        ), member.getId());

        // then
        List<Recommend> list = recommendRepository.findAll();
        assertThat(list).hasSize(1);

        Recommend rec = list.get(0);
        assertThat(rec.getPlace().getId()).isEqualTo(place1.getId());
        assertThat(rec.getReviewCount()).isEqualTo(1);
        assertThat(rec.getAverageRating()).isEqualTo(5);
    }

    // -------------------------------------------------

    @Test
    void 베이지안_가중치를_기준으로_TOP5_정렬된다() {
        // given
        // place1 = 5점
        reviewService.createReview(new ReviewRequestDto(place1.getId(), 5, "good"), member.getId());

        // place2 = 3점
        reviewService.createReview(new ReviewRequestDto(place2.getId(), 3, "normal"), member.getId());

        // place3 = 1점
        reviewService.createReview(new ReviewRequestDto(place3.getId(), 1, "bad"), member.getId());

        // when
        List<RecommendResponse> topList =
                reviewService.recommendPlace("HOTEL");

        // then
        assertThat(topList).hasSize(3);
        assertThat(topList.get(0).placeName()).isEqualTo("호텔A");
        assertThat(topList.get(1).placeName()).isEqualTo("호텔B");
        assertThat(topList.get(2).placeName()).isEqualTo("호텔C");
    }

    // -------------------------------------------------

    @Test
    void 전체_정렬_조회_sortPlaces() {
        // given
        reviewService.createReview(new ReviewRequestDto(place1.getId(), 4, "good"), member.getId());
        reviewService.createReview(new ReviewRequestDto(place2.getId(), 2, "bad"), member.getId());
        reviewService.createReview(new ReviewRequestDto(place3.getId(), 5, "great"), member.getId());

        // when
        List<RecommendResponse> sorted =
                reviewService.sortPlaces("HOTEL");

        // then
        assertThat(sorted).hasSize(3);
        assertThat(sorted.get(0).id()).isEqualTo(place1.getId());
        assertThat(sorted.get(1).id()).isEqualTo(place3.getId());
        assertThat(sorted.get(2).id()).isEqualTo(place2.getId());
    }
}
