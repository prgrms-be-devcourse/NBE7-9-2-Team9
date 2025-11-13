package com.backend.domain.plan.repository;

import com.backend.domain.member.entity.Member;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.entity.PlanMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlanMemberRepository extends JpaRepository<PlanMember, Long> {
    List<PlanMember> getPlanMembersByMember(Member member);

    List<PlanMember> getPlanMembersByPlan(Plan plan);

    @Query("""
SELECT COUNT(pm) > 0
FROM PlanMember pm
WHERE pm.plan.id = :planId
AND pm.member.id = :memberId
""")
    boolean existsByMemberInPlanId(
            @Param("memberId")long memberId,
            @Param("planId")long planId
    );

    void deletePlanMembersByPlanId(Long planId);

    void deletePlanMembersByPlan(Plan plan);

    List<PlanMember> getPlanMembersByMemberId(Long memberId);


}
