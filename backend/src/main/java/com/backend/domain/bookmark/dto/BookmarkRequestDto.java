package com.backend.domain.bookmark.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkRequestDto {
    private Long memberId;
    private Long placeId;
}
