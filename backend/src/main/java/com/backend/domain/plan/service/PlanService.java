package com.backend.domain.plan.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.plan.dto.PlanCreateRequestBody;
import com.backend.domain.plan.dto.PlanResponseBody;
import com.backend.domain.plan.dto.PlanUpdateRequestBody;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.entity.PlanMember;
import com.backend.domain.plan.repository.PlanMemberRepository;
import com.backend.domain.plan.repository.PlanRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanMemberRepository planMemberRepository;
    private final MemberRepository memberRepository; // 회원 정보 넣기 위한 임시 회원 레포지토리
    // TODO 회원 서비스 기반 처리 하기, JWT에서 멤버 ID 식별자 사용하면 더 편할것 같은데 보안상의 문제는 없는지?

    public Plan createPlan(PlanCreateRequestBody planCreateRequestBody, String memberId) {

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        Plan plan = new Plan(planCreateRequestBody, optionalMember.get());
        hasValidPlan(plan);
        Plan savedPlan = planRepository.save(plan);
        planMemberRepository.save(new PlanMember(optionalMember.get(), plan).inviteAccept());
        return savedPlan;
    }


    public List<PlanResponseBody> getPlanList(String memberID) {
        List<Plan> plans = planRepository.getPlansByMember_MemberId(memberID);
        List<PlanResponseBody> planResponseBodies = plans.stream().map(PlanResponseBody::new).toList();
        return planResponseBodies;
    }

    public PlanResponseBody updatePlan(long planId, PlanUpdateRequestBody planUpdateRequestBody, String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (optionalMember.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = optionalMember.get();
        Plan plan = getPlanById(planId);

        isSameMember(plan, member);
        hasValidPlan(plan);

        plan.updatePlan(planUpdateRequestBody, member);
        planRepository.save(plan);
        return new PlanResponseBody(plan);
    }

    public PlanResponseBody getPlanResponseBodyById(long planId) {
        return new PlanResponseBody(getPlanById(planId));
    }

    public Plan getPlanById(long planId) {
        return planRepository.findById(planId).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_PLAN)
        );
    }

    public void deletePlanById(long planId, String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        if (optionalMember.isEmpty()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        Plan plan = getPlanById(planId);
        isSameMember(plan, optionalMember.get());

        planRepository.deleteById(planId);
    }

    private void hasValidPlan(Plan plan) {
        if (plan.getStartDate().isAfter(plan.getEndDate())) {
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }
    }

    private void isSameMember(Plan plan, Member member) {
        if(member.getId() != plan.getMember().getId()){
            throw new BusinessException(ErrorCode.NOT_SAME_MEMBER);
        }
    }
}
