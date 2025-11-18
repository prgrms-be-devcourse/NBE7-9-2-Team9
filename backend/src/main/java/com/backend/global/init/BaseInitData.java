package com.backend.global.init;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.entity.Role;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.repository.PlaceRepository;
import com.backend.domain.review.entity.Review;
import com.backend.domain.review.repository.ReviewRepository;
import com.backend.domain.review.service.ReviewService;
import com.backend.external.seoul.hotel.service.HotelImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
@Profile("!test")
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PlaceRepository placeRepository;
    private final ReviewRepository reviewRepository;
    private final HotelImportService hotelImportService;
    private final ReviewService reviewService;
    @Bean
    public ApplicationRunner InitData() {
        return args -> {

            // 1. Member 초기 데이터
            if (memberRepository.count() == 0) {

                Member member1 = Member.builder()
                        .memberId("member1")
                        .password(passwordEncoder.encode("1111"))
                        .email("member1@gmail.com")
                        .nickname("사용자1")
                        .role(Role.USER)
                        .build();

                Member member2 = Member.builder()
                        .memberId("member2")
                        .password(passwordEncoder.encode("2222"))
                        .email("member2@gmail.com")
                        .nickname("사용자2")
                        .role(Role.ADMIN)
                        .build();

                Member admin = Member.builder()
                        .memberId("admin")
                        .password(passwordEncoder.encode("admin1234"))
                        .email("admin@gmail.com")
                        .nickname("관리자")
                        .role(Role.ADMIN)
                        .build();

                memberRepository.saveAll(List.of(member1, member2, admin));
                log.info("초기 member 데이터 세팅 완료");
            }

            // 2. HOTEL Place 데이터 자동 import (없을 때만)
            if (placeRepository.findByCategory_Name("HOTEL").isEmpty()) {
                int count = hotelImportService.importAll();
                log.info("호텔 {}개 import 완료", count);
            }

            // 3. HOTEL 리뷰 초기 데이터
            if (reviewRepository.count() == 0) {

                Member writer1 = memberRepository.findByMemberId("member1")
                        .orElseThrow(() -> new IllegalStateException("member1이 존재하지 않습니다."));
                Member writer2 = memberRepository.findByMemberId("member2")
                        .orElseThrow(() -> new IllegalStateException("member2가 존재하지 않습니다."));

                List<Place> hotelPlaces = placeRepository.findByCategory_Name("HOTEL");

                for (Place place : hotelPlaces) {

                    // member1 리뷰
                    int rating1 = (int) (((place.getId() - 1) % 5) + 1);
                    Review review1 = new Review(
                            place,
                            writer1,
                            rating1,
                            "이곳은 정말 멋진 장소입니다! 별점: " + rating1
                    );
                    review1.onCreate();
                    reviewRepository.save(review1);

                    // member2 리뷰
                    int rating2 = (int) (((place.getId()) % 5) + 1);
                    Review review2 = new Review(
                            place,
                            writer2,
                            rating2,
                            "여기도 꽤 괜찮네요! 별점: " + rating2
                    );
                    review2.onCreate();
                    reviewRepository.save(review2);

//                    place.setRatingCount(2);
                    placeRepository.save(place);

                    // ⭐ 추천 테이블 업데이트 (베이지안 평균 계산)
                    reviewService.updateRecommend(place);
                }

                log.info("HOTEL 리뷰 초기 데이터 세팅 완료. 생성된 리뷰 수 = {}", hotelPlaces.size() * 2);
            }
        };
    }
}
