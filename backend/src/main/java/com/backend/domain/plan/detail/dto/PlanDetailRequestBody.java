package com.backend.domain.plan.detail.dto;

import com.backend.domain.plan.detail.entity.PlanDetail;
import com.backend.global.reponse.ErrorCode;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PlanDetailRequestBody(
        @NotNull(message = "계획이 누락되었습니다.")
        long planId,
        @NotNull(message = "장소가 누락되었습니다.")
        long placeId,
        @NotNull
        LocalDateTime startTime,
        @NotNull
        LocalDateTime endTime,
        @NotNull
        String title,
        String content
) {

}
