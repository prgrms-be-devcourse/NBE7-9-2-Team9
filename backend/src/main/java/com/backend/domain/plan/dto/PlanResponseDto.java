package com.backend.domain.plan.dto;

import com.backend.domain.plan.entity.Plan;
import lombok.Getter;

import java.time.LocalDateTime;

public record PlanResponseDto(
        String title,
        String content,
        LocalDateTime startDate,
        LocalDateTime EndDate
) {
    public PlanResponseDto(Plan plan) {
        this(
                plan.getTitle(),
                plan.getContent(),
                plan.getStartDate(),
                plan.getEndDate()
        );
    }

    public PlanResponseDto(String title, String content, LocalDateTime startDate, LocalDateTime EndDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.EndDate = EndDate;
    }
}
