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
        if(optionalMember.isEmpty()){
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        Plan plan = new Plan(planCreateRequestBody,optionalMember.get());
        isValidPlan(plan);
        Plan savedPlan = planRepository.save(plan);
        planMemberRepository.save(new PlanMember(optionalMember.get(),plan).inviteAccept());
        return savedPlan;
    }


    public List<PlanResponseBody> getPlanList(String memberID) {
         List<Plan> plans= planRepository.getPlansByMember_MemberId(memberID);
        List<PlanResponseBody> planResponseBodies = plans.stream().map(PlanResponseBody::new).toList();
        return planResponseBodies;
    }

    public PlanResponseBody updatePlan(long planId, PlanUpdateRequestBody planUpdateRequestBody, String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        Optional<Plan> optionalPlan = planRepository.findById(planId);

        if(optionalMember.isEmpty()){
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }
        if(optionalPlan.isEmpty()){
            throw new BusinessException(ErrorCode.NOT_FOUND_PLAN);
        }

        Member member = optionalMember.get();
        Plan plan = optionalPlan.get();

        if(plan.getMember().getId() != member.getId()){
            throw new BusinessException(ErrorCode.NOT_SAME_MEMBER);
        }

        isValidPlan(plan);

        plan.updatePlan(planUpdateRequestBody,member);
        planRepository.save(plan);
        return new PlanResponseBody(plan);
    }

    private void isValidPlan(Plan plan){
        if(plan.getStartDate().isAfter(plan.getEndDate())){
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }
    }

    public PlanResponseBody getPlanById(long planId) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        if(optionalPlan.isEmpty()){
            throw new BusinessException(ErrorCode.NOT_FOUND_PLAN);
        }
        return new PlanResponseBody(optionalPlan.get());
    }

    public void deletePlanById(long planId, String memberId) {
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if(optionalPlan.isEmpty()){
            throw new BusinessException(ErrorCode.NOT_FOUND_PLAN);
        }

        if(optionalMember.isEmpty()){
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        Plan plan = optionalPlan.get();
        planRepository.deleteById(planId);

    }
}
