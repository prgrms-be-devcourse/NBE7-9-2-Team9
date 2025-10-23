package com.backend.domain.bookmark.repository;

import com.backend.domain.bookmark.entity.Bookmark;
import com.backend.domain.member.entity.Member;
import com.backend.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // 소프트 삭제 미포함 상태의 북마크가 존재하는지
    Optional<Bookmark> findByMemberAndPlaceAndDeletedAtIsNull(Member member, Place place);

    // 소프트 삭제 포함(존재여부 확인용)
    Optional<Bookmark> findByMemberAndPlace(Member member, Place place);

    // 회원의 활성 북마크 목록 (최근 저장 순)
    List<Bookmark> findAllByMemberAndDeletedAtIsNullOrderByCreatedAtDesc(Member member);
}
