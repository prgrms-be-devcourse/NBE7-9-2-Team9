package com.backend.domain.plan.repository;

import com.backend.domain.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> getPlansByMember_MemberId(String memberID);

    Optional<Plan> getPlanById(Long id);

    List<Plan> getPlansByMember_Id(Long memberId);
}
