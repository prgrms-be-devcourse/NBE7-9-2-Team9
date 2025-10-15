package com.backend.domain.bookmark.dto;


import com.backend.domain.bookmark.entity.Bookmark;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookmarkResponseDto {

    private Long bookmarkId;
    private Long placeId;
    private String placeName;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public static BookmarkResponseDto from(Bookmark b) {
        BookmarkResponseDto dto = new BookmarkResponseDto();
        dto.bookmarkId = b.getBookmarkId();
        dto.placeId = b.getPlace().getId();
        dto.placeName = b.getPlace().getPlaceName();
        dto.address = b.getPlace().getAddress();
        dto.createdAt = b.getCreatedAt();
        dto.deletedAt = b.getDeletedAt();
        return dto;
    }
}
