package com.backend.domain.bookmark.dto;

import com.backend.domain.bookmark.entity.Bookmark;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BookmarkResponseDto(
        Long bookmarkId,
        Long memberId,
        Long placeId,
        String placeName,
        String address,
        LocalDateTime createdAt,
        LocalDateTime deletedAt
)
{
    public static BookmarkResponseDto from(Bookmark bookmark) {
        return BookmarkResponseDto.builder()
                .bookmarkId(bookmark.getBookmarkId())
                .memberId(bookmark.getMember().getId())
                .placeId(bookmark.getPlace().getId())
                .placeName(bookmark.getPlace().getPlaceName())
                .address(bookmark.getPlace().getAddress())
                .createdAt(bookmark.getCreatedAt())
                .deletedAt(bookmark.getDeletedAt())
                .build();
    }
}
