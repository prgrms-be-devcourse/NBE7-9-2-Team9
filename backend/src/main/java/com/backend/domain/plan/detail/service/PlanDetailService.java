package com.backend.domain.plan.detail.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.place.entity.Place;
import com.backend.domain.plan.detail.dto.PlanDetailRequestBody;
import com.backend.domain.plan.detail.dto.PlanDetailResponseBody;
import com.backend.domain.plan.detail.dto.PlanDetailsElementBody;
import com.backend.domain.plan.detail.entity.PlanDetail;
import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.entity.PlanMember;
import com.backend.domain.plan.repository.PlanMemberRepository;
import com.backend.domain.plan.repository.PlanRepository;
import com.backend.domain.plan.service.PlanService;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanDetailService {
    private final PlanService planService;
    private final PlanDetailRepository planDetailRepository;
    //TODO 각자 서비스 객체가 만들어지면 레포지토리 말고 서비스에서 해당 객체를 가져오기? 아니 아이디만 있으면 되잖아?
    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;
    private final PlanMemberRepository planMemberRepository;


    public PlanDetail addPlanDetail(PlanDetailRequestBody requestBody,String memberId) {
        //TODO 아래 부분 수정하기
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (!optionalMember.isPresent()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        checkAvailableTime(requestBody);

        Member member = optionalMember.get();
        Plan plan = planService.getPlanById(requestBody.planId());

        Place place = new Place();
        place.setId(requestBody.placeId());

        PlanDetail planDetail = new PlanDetail(member,plan,place,requestBody);
        this.planDetailRepository.save(planDetail);
        return planDetail;
    }


    public PlanDetailsElementBody getPlanDetailById(Long planDetailId,String memberId) {


        Optional<PlanDetail> optionalPlanDetail = planDetailRepository.getPlanDetailById(planDetailId);
        if (!optionalPlanDetail.isPresent()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_DETAIL_PLAN);
        }


        PlanDetail planDetail = optionalPlanDetail.get();
        isAvailableMember(planDetail.getPlan().getId(),memberRepository.findByMemberId(memberId).get());

        return new PlanDetailsElementBody(planDetail);
    }

    @Transactional
    public List<PlanDetailsElementBody> getPlanDetailsByPlanId(long planId, String memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        isAvailableMember(planId,member);

        List<PlanDetail> planDetails = planDetailRepository.getPlanDetailsByPlan_Id(planId);

        List<PlanDetailsElementBody> planDetailList = planDetails.stream()
                .map(
                        (PlanDetail p) ->
                                new PlanDetailsElementBody(p)
                )
                .toList();

        return planDetailList;
    }

    public PlanDetailResponseBody updatePlanDetail(PlanDetailRequestBody planDetailRequestBody,String memberId) {

        return null;
    }

    private boolean isAvailableMember(long planId, Member member) {
        Plan plan = planService.getPlanById(planId);
        List<PlanMember> planMembers = planMemberRepository.getPlanMembersByPlan(plan);

        PlanMember planMember = planMembers.stream()
                .filter(
                        target -> target.getMember().getId().equals(member.getId())
                )
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_ALLOWED_MEMBER));

        if(!planMember.isConfirmed()) throw new BusinessException(ErrorCode.NOT_ACCEPTED_MEMBER);

        return true;
    }

    private void checkAvailableTime(PlanDetailRequestBody planDetailRequestBody) {
        if(planDetailRepository.existsOverlapping(planDetailRequestBody.planId(),planDetailRequestBody.startTime(),planDetailRequestBody.endTime())) {
            throw new BusinessException(ErrorCode.CONFLICT_TIME);
        }

    }


}
