package com.backend.domain.plan.detail.dto;

import com.backend.domain.plan.detail.entity.PlanDetail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PlanDetailResponseBody(
        @NotNull
        long placeId,
        @NotEmpty
        LocalDateTime startTime,
        @NotEmpty
        LocalDateTime endTime,
        @NotEmpty
        String title,
        @NotEmpty
        String content
) {
    public PlanDetailResponseBody(long placeId, LocalDateTime startTime, LocalDateTime endTime, String title, String content) {
        this.placeId = placeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
    }

    public PlanDetailResponseBody(PlanDetail planDetail) {
        this(
                planDetail.getPlace().getId(),
                planDetail.getStartTime(),
                planDetail.getEndTime(),
                planDetail.getTitle(),
                planDetail.getContent()
        );
    }
}
