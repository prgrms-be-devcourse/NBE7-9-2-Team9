package com.backend.domain.bookmark.dto;

import jakarta.validation.constraints.NotNull;

public record BookmarkRequestDto(
        @NotNull Long placeId
) {}
