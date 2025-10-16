package com.backend.domain.plan.dto;

public record PlanMemberAddRequestBody(
        long memberId,
        long planId
) {
}
