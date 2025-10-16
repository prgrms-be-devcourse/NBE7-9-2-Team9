package com.backend.domain.plan.dto;

public record PlanMemberAnswerRequestBody(
        long planMemberId,
        long memberId,
        long planId
) {
}
