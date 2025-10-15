package com.backend.domain.plan.detail.dto;

import java.time.LocalDateTime;

public record PlanDetailsElementBody(
        long id,
        long placeId,
        String placeName,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String title,
        String content
) {
    public PlanDetailsElementBody(long id, long placeId, String placeName, LocalDateTime startTime, LocalDateTime endTime, String title, String content) {
        this.id = id;
        this.placeId = placeId;
        this.placeName = placeName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
    }
}
