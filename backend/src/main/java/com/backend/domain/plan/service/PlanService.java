package com.backend.domain.plan.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.service.MemberService;
import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.dto.PlanCreateRequestBody;
import com.backend.domain.plan.dto.PlanResponseBody;
import com.backend.domain.plan.dto.PlanUpdateRequestBody;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.entity.PlanMember;
import com.backend.domain.plan.repository.PlanMemberRepository;
import com.backend.domain.plan.repository.PlanRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanMemberRepository planMemberRepository;
    private final MemberService memberService;
    private final PlanDetailRepository planDetailRepository;

    @Transactional
    public Plan createPlan(PlanCreateRequestBody planCreateRequestBody, long memberPkId) {
        Member member = Member.builder().id(memberPkId).build();
        Plan plan = planCreateRequestBody.toEntity(member);
        hasValidPlan(plan);
        Plan savedPlan = planRepository.save(plan);
        planMemberRepository.save(PlanMember.builder().member(member).plan(plan).build().inviteAccept()); // 단순 저장이므로 레포지토리 사용.
        return savedPlan;
    }


    public List<PlanResponseBody> getPlanList(long memberPkId) {
        List<Plan> plans = planRepository.getPlansByMember_Id(memberPkId);
        List<PlanResponseBody> planResponseBodies = plans.stream().map(PlanResponseBody::new).toList();
        return planResponseBodies;
    }

    public List<PlanResponseBody> getInvitedPlanList(long memberPkId) {
        List<Plan> plans = planRepository.getPlansInvitedListByMemberPkId(memberPkId);
        List<PlanResponseBody> planResponseBodies = plans.stream().map(PlanResponseBody::new).toList();
        return planResponseBodies;
    }

    @Transactional
    public PlanResponseBody updatePlan(long planId, PlanUpdateRequestBody planUpdateRequestBody, long memberPkId) {
        Member member = Member.builder().id(memberPkId).build();
        Plan plan = getPlanById(planId);
        isSameMember(plan, member);
        hasValidPlan(plan);

        plan.updatePlan(planUpdateRequestBody, member);
        planRepository.save(plan);
        return new PlanResponseBody(plan);
    }

    @Transactional
    public void deletePlanById(long planId, long memberPkId) {
        Plan plan = getPlanById(planId);
        Member member = Member.builder().id(memberPkId).build();
        isSameMember(plan, member);
        planMemberRepository.deletePlanMembersByPlan(plan);
        planDetailRepository.deletePlanDetailsByPlan(plan);
        planRepository.deleteById(planId);
    }

    public PlanResponseBody getPlanResponseBodyById(long planId) {
        return new PlanResponseBody(getPlanById(planId));
    }

    public Plan getPlanById(long planId) {
        return planRepository.findById(planId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_PLAN)
        );
    }

    // Todo 계획의 범위가 오늘 내로 들어 있다면 반환하게 만들기
    public PlanResponseBody getTodayPlan(long memberPkId){
        LocalDateTime todayStart = LocalDateTime.now().toLocalDate().atStartOfDay();
        List<Plan>  plans =planRepository.getPlanByStartDateBeforeAndEndDateAfterAndMemberId(LocalDateTime.now().toLocalDate().atStartOfDay().plusSeconds(1),LocalDateTime.now().toLocalDate().atTime(LocalTime.MAX).minusSeconds(1),memberPkId);

        Plan plan = planRepository.getPlanByStartDateAndMemberId(todayStart, memberPkId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_PLAN)
        );
        return new PlanResponseBody(plans.get(0));
    }

    private void hasValidPlan(Plan plan) {
        if (plan.getStartDate().isAfter(plan.getEndDate())) {
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }
        if (plan.getStartDate().isBefore(LocalDateTime.now().toLocalDate().atStartOfDay().minusSeconds(1))) {
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }
        if (plan.getEndDate().isAfter(LocalDateTime.now().plusYears(10))) {
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }
    }

    private void isSameMember(Plan plan, Member member) {
        if (member.getId() != plan.getMember().getId()) {
            throw new BusinessException(ErrorCode.NOT_SAME_MEMBER);
        }
    }


}
