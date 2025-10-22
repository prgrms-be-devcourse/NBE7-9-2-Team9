package com.backend.domain.plan.dto;

import jakarta.validation.constraints.NotNull;

public record PlanMemberAddRequestBody(
        @NotNull
        long memberId,
        @NotNull
        long planId
) {
}
