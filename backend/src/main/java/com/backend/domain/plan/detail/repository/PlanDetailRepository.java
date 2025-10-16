package com.backend.domain.plan.detail.repository;

import com.backend.domain.plan.detail.entity.PlanDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PlanDetailRepository extends JpaRepository<PlanDetail, Long> {
    Optional<PlanDetail> getPlanDetailById(Long planDetailId);

    List<PlanDetail> getPlanDetailsByPlan_Id(long planId);

    @Query("""
SELECT COUNT(pd)>0
FROM PlanDetail pd
WHERE pd.plan.id = :planId
AND NOT (:endTime < pd.startTime OR :startTime > pd.endTime)
""")
    boolean existsOverlapping(
            @Param("planId")long PlanId,
            @Param("endTime")LocalDateTime startTime,
            @Param("startTime")LocalDateTime endTime
    );

}
