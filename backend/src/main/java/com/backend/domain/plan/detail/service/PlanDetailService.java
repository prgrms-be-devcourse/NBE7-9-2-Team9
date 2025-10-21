package com.backend.domain.plan.detail.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.service.MemberService;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.repository.PlaceRepository;
import com.backend.domain.plan.detail.dto.PlanDetailRequestBody;
import com.backend.domain.plan.detail.dto.PlanDetailResponseBody;
import com.backend.domain.plan.detail.dto.PlanDetailsElementBody;
import com.backend.domain.plan.detail.entity.PlanDetail;
import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.service.PlanMemberService;
import com.backend.domain.plan.service.PlanService;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanDetailService {
    private final PlanService planService;
    private final PlanMemberService planMemberService;
    private final MemberService memberService;

    //TODO 각자 서비스 객체가 만들어지면 레포지토리 말고 서비스에서 해당 객체를 가져오기? 아니 아이디만 있으면 되잖아?
    private final PlaceRepository placeRepository;
    private final PlanDetailRepository planDetailRepository;


    public PlanDetail addPlanDetail(PlanDetailRequestBody requestBody, String memberId) {


        Member member = getAvailableMember(requestBody.planId(),  memberId);
        Plan plan = planService.getPlanById(requestBody.planId());
        Place place = placeRepository.getPlaceById(requestBody.placeId());

        PlanDetail planDetail = new PlanDetail(member, plan, place, requestBody);
        checkValidTime(requestBody, plan,planDetail);
        this.planDetailRepository.save(planDetail);
        return planDetail;
    }


    public PlanDetailsElementBody getPlanDetailById(Long planDetailId, String memberId) {
        PlanDetail planDetail = getPlanDetailById(planDetailId);
        getAvailableMember(planDetail.getPlan().getId(), memberId);

        return new PlanDetailsElementBody(planDetail);
    }

    @Transactional
    public List<PlanDetailsElementBody> getPlanDetailsByPlanId(long planId, String memberId) {
        getAvailableMember(planId, memberId);

        List<PlanDetail> planDetails = planDetailRepository.getPlanDetailsByPlanId(planId);

        List<PlanDetailsElementBody> planDetailList = planDetails.stream()
                .map(
                        (PlanDetail p) ->
                                new PlanDetailsElementBody(p)
                )
                .toList();

        return planDetailList;
    }

    public PlanDetailResponseBody updatePlanDetail(PlanDetailRequestBody planDetailRequestBody, String memberId, long planDetailId) {
        getAvailableMember(planDetailRequestBody.planId(), memberId);


        Place place = placeRepository.getPlaceById(planDetailRequestBody.placeId());
        PlanDetail planDetail = getPlanDetailById(planDetailId);
        checkValidTime(planDetailRequestBody, planService.getPlanById(planDetailId), planDetail);
        planDetail.updatePlanDetail(planDetailRequestBody, place);
        planDetailRepository.save(planDetail);
        return new PlanDetailResponseBody(planDetail);
    }

    public void deletePlanDetail(long planDetailId, String memberId) {
        getAvailableMember(planDetailId, memberId);
        planDetailRepository.deleteById(planDetailId);
    }



    private PlanDetail getPlanDetailById(long planDetailId) {
        return planDetailRepository.getPlanDetailById(planDetailId).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_DETAIL_PLAN));
    }


    private Member getAvailableMember(long planId, String memberId) {
        Member member = memberService.findByMemberId(memberId);
        Plan plan = planService.getPlanById(planId);
        if (!planMemberService.isAvailablePlanMember(planId, member)) {
            throw new BusinessException(ErrorCode.NOT_ALLOWED_MEMBER);
        }
        return member;
    }

    //시간이 유효한 시간인지
    private void checkValidTime(PlanDetailRequestBody planDetailRequestBody, Plan plan,PlanDetail planDetail) {
        // 계획 내에서 시간이 겹치지 않는지 검사
        if (planDetailRepository.existsOverlapping(planDetailRequestBody.planId(), planDetailRequestBody.startTime(), planDetailRequestBody.endTime(), planDetail.getId())) {
            throw new BusinessException(ErrorCode.CONFLICT_TIME);
        }

        //계획 안의 시간인지
        if(planDetailRequestBody.startTime().isBefore(plan.getStartDate()) || planDetailRequestBody.endTime().isBefore(plan.getEndDate())) {
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }

        // 지금으로부터 10년 뒤 까지만 계획 설정 가능
        if (planDetailRequestBody.startTime().isAfter(LocalDateTime.now().plusYears(10))) {
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }
    }


}
