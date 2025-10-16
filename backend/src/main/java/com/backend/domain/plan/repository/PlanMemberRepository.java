package com.backend.domain.plan.repository;

import com.backend.domain.member.entity.Member;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.entity.PlanMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanMemberRepository extends JpaRepository<PlanMember, Long> {
    List<PlanMember> getPlanMembersByMember(Member member);

    List<PlanMember> getPlanMembersByPlan(Plan plan);
}
