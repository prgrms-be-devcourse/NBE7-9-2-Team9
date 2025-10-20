package com.backend.domain.plan.dto;

import jakarta.validation.constraints.NotNull;

public record PlanMemberAnswerRequestBody(
        @NotNull
        long planMemberId,
        @NotNull
        long memberId,
        @NotNull
        long planId
) {
}
