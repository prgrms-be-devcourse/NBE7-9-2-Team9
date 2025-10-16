package com.backend.domain.plan.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.plan.dto.MyPlanMemberResponseBody;
import com.backend.domain.plan.dto.PlanMemberAddRequestBody;
import com.backend.domain.plan.dto.PlanMemberAnswerRequestBody;
import com.backend.domain.plan.dto.PlanMemberResponseBody;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.entity.PlanMember;
import com.backend.domain.plan.repository.PlanMemberRepository;
import com.backend.domain.plan.repository.PlanRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlanMemberService {
    private final PlanMemberRepository planMemberRepository;
    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;

    public PlanMemberResponseBody invitePlanMember(PlanMemberAddRequestBody requestBody, String memberId) {
        PlanMember planMember = isValidInvite(requestBody, memberId);

        try{
            planMemberRepository.save(planMember);
        } catch (DataIntegrityViolationException e){
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.DUPLICATE_MEMBER_INVITE);
        }

        return new PlanMemberResponseBody(planMember);
    }

    public List<MyPlanMemberResponseBody> myInvitedPlanList(String memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND)
        );

        List<PlanMember> planMemberList = planMemberRepository.getPlanMembersByMember(member);
        List<MyPlanMemberResponseBody> myPlanMemberList =
                planMemberList
                        .stream()
                        .map(pm -> new MyPlanMemberResponseBody(pm))
                        .toList();
        return myPlanMemberList;
    }

    public PlanMemberResponseBody DeletePlanMember(PlanMemberAddRequestBody requestBody, String memberId) {
        PlanMember planMember = isValidInvite(requestBody, memberId);
        // TODO isValidInvite가 사실상 새로운 객체를 반환하므로 인식 안되는 문제 해결할 것.
        planMemberRepository.delete(planMember);
        return new PlanMemberResponseBody(planMember);
    }

    public PlanMemberResponseBody acceptInvitePlanMember(PlanMemberAnswerRequestBody requestBody, String memberId) {
        PlanMember planMember = isMyInvite(requestBody, memberId);
        planMember.inviteAccept();
        planMemberRepository.save(planMember);
        return new PlanMemberResponseBody(planMember);
    }

    public PlanMemberResponseBody denyInvitePlanMember(PlanMemberAnswerRequestBody requestBody, String memberId) {
        PlanMember planMember = isMyInvite(requestBody, memberId);
        planMember.inviteDeny();
        planMemberRepository.save(planMember);
        return new PlanMemberResponseBody(planMember);
    }

    private PlanMember isValidInvite(PlanMemberAddRequestBody requestBody, String memberId) {
        Member myMember = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND)
        );

        Plan plan = planRepository.getPlanById(requestBody.planId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_PLAN)
        );

        if (plan.getMember().getId() != myMember.getId()) {
            throw new BusinessException(ErrorCode.NOT_MY_PLAN);
        }

        Member invitedMember = memberRepository.findById(requestBody.memberId()).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND)
        );

        PlanMember planMember = new PlanMember(invitedMember, plan);
        return planMember;
    }

    private PlanMember isMyInvite(PlanMemberAnswerRequestBody requestBody, String memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(
                () -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND)
        );
        Plan plan = planRepository.getPlanById(requestBody.planId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_PLAN)
        );

        if (requestBody.memberId() != member.getId()) {
            throw new BusinessException(ErrorCode.NOT_MY_PLAN);
        }


        PlanMember planMember = planMemberRepository.findById(requestBody.planMemberId()).orElseThrow(
                () -> new BusinessException(ErrorCode.NOT_FOUND_INVITE)
        );

        return planMember;
    }

    public boolean isAvailablePlanMember(long planId, Member member) {
        boolean isAvailable = false;



        return isAvailable;
    }
}
