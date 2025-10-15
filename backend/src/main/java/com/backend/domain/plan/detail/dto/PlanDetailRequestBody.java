package com.backend.domain.plan.detail.dto;

import com.backend.domain.plan.detail.entity.PlanDetail;

import java.time.LocalDateTime;

public record PlanDetailRequestBody(
        long planId,
        long placeId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String title,
        String content
) {

}
