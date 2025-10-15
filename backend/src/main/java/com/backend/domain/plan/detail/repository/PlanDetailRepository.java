package com.backend.domain.plan.detail.repository;

import com.backend.domain.plan.detail.entity.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlanDetailRepository extends JpaRepository<PlanDetail, Long> {
    Optional<PlanDetail> getPlanDetailById(Long planDetailId);

    List<PlanDetail> getPlanDetailsByPlan_Id(long planId);
}
