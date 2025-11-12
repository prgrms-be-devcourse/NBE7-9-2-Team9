package com.backend.domain.plan.repository;

import com.backend.domain.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan> getPlansByMember_MemberId(String memberID);

    Optional<Plan> getPlanById(Long id);

    List<Plan> getPlansByMember_Id(Long memberId);

    Optional<Plan> getPlanByStartDateAndMemberId(LocalDateTime startDate, Long memberId);

    Plan getPlanByTitle(String title);

    Plan getPlanByStartDateBeforeAndEndDateAfter(LocalDateTime startDateBefore, LocalDateTime endDateAfter);

    List<Plan> getPlanByStartDateBeforeAndEndDateAfterAndMemberId(LocalDateTime startDateBefore, LocalDateTime endDateAfter, Long memberId);

    @Query("""
SELECT
Plan plan
FROM 
Plan p,
PlanMember pm
WHERE
p.id = pm.plan.id
AND
pm.member.id = :memberId
AND
pm.isConfirmed = 1
""")
    List<Plan> getPlansInvitedListByMemberPkId(
            @Param("memberId") Long memberPkId
    );
}
