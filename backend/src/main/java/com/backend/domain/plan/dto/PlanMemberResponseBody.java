package com.backend.domain.plan.dto;

import com.backend.domain.plan.entity.PlanMember;

public record PlanMemberResponseBody(
        String memberLoginId,
        String planTitle
) {
    public PlanMemberResponseBody(String memberLoginId, String planTitle) {
        this.memberLoginId = memberLoginId;
        this.planTitle = planTitle;
    }

    public PlanMemberResponseBody(PlanMember planMember){
        this(
                planMember.getMember().getMemberId(),
                planMember.getPlan().getTitle()
        );
    }
}
