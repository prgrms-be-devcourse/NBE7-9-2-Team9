package com.backend.domain.plan.dto;

import com.backend.domain.member.entity.Member;
import com.backend.domain.plan.entity.Plan;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.constraints.NotEmpty;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public record PlanCreateRequestBody(
        @NotEmpty
        String title,
        String content,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate
) {
    public PlanCreateRequestBody(String title, String content, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Plan toEntity(Member member) {
        return Plan.builder()
                .member(member)
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .title(title)
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
