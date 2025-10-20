package com.backend.domain.plan.dto;

import com.backend.domain.plan.entity.Plan;

import java.time.LocalDateTime;

public record PlanResponseBody(
        String title,
        String content,
        LocalDateTime startDate,
        LocalDateTime EndDate
) {
    public PlanResponseBody(Plan plan) {
        this(
                plan.getTitle(),
                plan.getContent(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }

    public PlanResponseBody(String title, String content, LocalDateTime startDate, LocalDateTime EndDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.EndDate = EndDate;
    }
}
