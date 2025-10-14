package com.backend.domain.plan.dto;

import com.backend.domain.plan.entity.Plan;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public record PlanCreateRequestDto(
        @NotNull
        String title,
        @NotNull
        String content,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate
) {
    public PlanCreateRequestDto(String title, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Plan createPlan(){
        return new Plan(
                this.startDate,
                this.endDate,
                this.title,
                this.content
        );
    }
}
