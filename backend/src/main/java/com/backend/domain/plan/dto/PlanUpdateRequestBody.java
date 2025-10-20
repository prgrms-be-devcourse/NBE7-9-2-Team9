package com.backend.domain.plan.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public record PlanUpdateRequestBody (
        @NotNull
        long id,
        @NotEmpty
        String title,
        String content,
        @NotNull
        LocalDateTime startDate,
        @NotNull
        LocalDateTime endDate
){
}
