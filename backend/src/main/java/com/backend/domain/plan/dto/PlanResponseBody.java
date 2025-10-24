package com.backend.domain.plan.dto;

import com.backend.domain.plan.entity.Plan;

import java.time.LocalDateTime;

public record PlanResponseBody(
        long id,
        String title,
        String content,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
    public PlanResponseBody(Plan plan) {
        this(
                plan.getId(),
                plan.getTitle(),
                plan.getContent(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }

    public PlanResponseBody(long id, String title, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
