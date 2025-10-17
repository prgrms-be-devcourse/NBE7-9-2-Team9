package com.backend.domain.plan.dto;

public record PlanMemberAddRequestBody(
        String memberId,
        long planId
) {
}
