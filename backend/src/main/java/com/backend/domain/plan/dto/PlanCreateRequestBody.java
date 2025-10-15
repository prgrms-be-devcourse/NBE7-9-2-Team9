package com.backend.domain.plan.dto;

import com.backend.domain.plan.entity.Plan;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public record PlanCreateRequestBody(
        @NotNull
        String title,
        @NotNull
        String content,
        @NotNull
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime startDate,
        @NotNull
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        LocalDateTime endDate
) {
    public PlanCreateRequestBody(String title, String content, LocalDateTime startDate, LocalDateTime endDate) {
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
