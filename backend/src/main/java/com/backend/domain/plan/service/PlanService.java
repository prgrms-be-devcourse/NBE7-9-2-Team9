package com.backend.domain.plan.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.plan.controller.PlanController;
import com.backend.domain.plan.dto.PlanCreateRequestDto;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.repository.PlanRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final MemberRepository memberRepository; // 회원 정보 넣기 위한 임시 회원 레포지토리


    public Plan CreatePlan(PlanCreateRequestDto planCreateRequestDto,String memberId) {
        if(planCreateRequestDto.startDate().isAfter(planCreateRequestDto.endDate())){
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }

        Member member= memberRepository.findByMemberId(memberId);
        Plan plan = new Plan(planCreateRequestDto, member);
        return planRepository.save(plan);
    }

    public List<Plan> getMyPlans(String memberId) {

    }


}
