package com.backend.domain.plan.repository;

import com.backend.domain.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> getPlansByMember_MemberId(String memberID);
}
