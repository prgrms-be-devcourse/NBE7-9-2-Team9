package com.backend.domain.plan.dto;

import com.backend.domain.plan.entity.PlanMember;

public record PlanMemberMyResponseBody(
        long planMemberId,
        String memberLoginId,
        String planTitle,
        String isAccepted
) {
    public PlanMemberMyResponseBody(long planMemberId, String memberLoginId, String planTitle, String isAccepted) {
        this.planMemberId = planMemberId;
        this.memberLoginId = memberLoginId;
        this.planTitle = planTitle;
        this.isAccepted = isAccepted;
    }

    public PlanMemberMyResponseBody(PlanMember planMember) {
        this(
                planMember.getId(),
                planMember.getMember().getMemberId(),
                planMember.getPlan().getTitle(),
                planMember.inviteStatusString()
        );
    }
}
