package com.backend.domain.bookmark.service;

import com.backend.domain.bookmark.dto.BookmarkRequestDto;
import com.backend.domain.bookmark.dto.BookmarkResponseDto;
import com.backend.domain.bookmark.entity.Bookmark;
import com.backend.domain.bookmark.exception.AlreadyExistsException;
import com.backend.domain.bookmark.exception.NotFoundException;
import com.backend.domain.bookmark.repository.BookmarkRepository;
import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final PlaceRepository placeRepository;

    /**
     * 북마크 생성
     * - 동일 조합(활성) 존재 시 예외
     * - 이미 소프트 삭제된 엔티티가 있으면 재활성화(삭제일 제거, createdAt 갱신)
     */
    @Transactional
    public BookmarkResponseDto create(BookmarkRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundException("Member not found"));
        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new NotFoundException("Place not found"));

        // 활성 상태의 북마크가 이미 있으면 중복
        bookmarkRepository.findByMemberAndPlaceAndDeletedAtIsNull(member, place)
                .ifPresent(b -> { throw new AlreadyExistsException("이미 북마크된 장소입니다."); });

        // 소프트 삭제된 항목이 있었으면 재활성화
        var maybe = bookmarkRepository.findByMemberAndPlace(member, place);
        if (maybe.isPresent()) {
            Bookmark existing = maybe.get();
            existing.setDeletedAt(null);
            existing.setCreatedAt(LocalDateTime.now());
            Bookmark saved = bookmarkRepository.save(existing);
            return BookmarkResponseDto.from(saved);
        }

        // 신규 생성
        Bookmark bookmark = Bookmark.create(member, place);
        Bookmark saved = bookmarkRepository.save(bookmark);
        return BookmarkResponseDto.from(saved);
    }

    /**
     * 북마크 목록 조회 (최근 저장 순), read-only 트랜잭션
     */
    @Transactional(readOnly = true)
    public List<BookmarkResponseDto> getList(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        return bookmarkRepository.findAllByMemberAndDeletedAtIsNullOrderByCreatedAtDesc(member)
                .stream()
                .map(BookmarkResponseDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 소프트 삭제: deletedAt = now()
     * 소유자 체크 수행
     */
    @Transactional
    public void delete(Long memberId, Long bookmarkId) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
                .orElseThrow(() -> new NotFoundException("Bookmark not found"));

        if (!bookmark.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        if (bookmark.isDeleted()) {
            return; // 이미 삭제된 상태면 idempotent 처리
        }

        bookmark.delete(); // 엔티티 내 헬퍼 사용
        bookmarkRepository.save(bookmark);
    }
}
