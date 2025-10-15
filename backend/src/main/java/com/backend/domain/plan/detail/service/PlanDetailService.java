package com.backend.domain.plan.detail.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.place.entity.Place;
import com.backend.domain.plan.detail.dto.PlanDetailRequestBody;
import com.backend.domain.plan.detail.entity.PlanDetail;
import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.repository.PlanRepository;
import com.backend.domain.plan.service.PlanService;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanDetailService {
    private final PlanService planService;
    private final PlanDetailRepository planDetailRepository;
    //TODO 각자 서비스 객체가 만들어지면 레포지토리 말고 서비스에서 해당 객체를 가져오기? 아니 아이디만 있으면 되잖아?
    private final MemberRepository memberRepository;


    public PlanDetail addPlanDetail(PlanDetailRequestBody requestBody,String memberId) {
        //TODO 아래 부분 수정하기
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if (!optionalMember.isPresent()) {
            throw new BusinessException(ErrorCode.MEMBER_NOT_FOUND);
        }

        Member member = optionalMember.get();
        Plan plan = new Plan(member,null,null,null,null);
        Place place = new Place();
        place.setId(requestBody.placeId());

        PlanDetail planDetail = new PlanDetail(member,plan,place,requestBody);
        this.planDetailRepository.save(planDetail);
        return planDetail;
    }



}
