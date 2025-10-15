package com.backend.domain.plan.service;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.plan.controller.PlanController;
import com.backend.domain.plan.dto.PlanCreateRequestDto;
import com.backend.domain.plan.dto.PlanResponseDto;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.repository.PlanRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final MemberRepository memberRepository; // 회원 정보 넣기 위한 임시 회원 레포지토리
    // TODO 회원 서비스 기반 처리 하기, JWT에서 멤버 ID 식별자 사용하면 더 편할것 같은데 보안상의 문제는 없는지?

    public Plan CreatePlan(PlanCreateRequestDto planCreateRequestDto,String memberId) {

        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);
        if(optionalMember.isEmpty()){
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }

        if(planCreateRequestDto.startDate().isAfter(planCreateRequestDto.endDate())){
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }

        Plan plan = new Plan(planCreateRequestDto,optionalMember.get());
        return planRepository.save(plan);
    }


    public List<PlanResponseDto> getPlanList(String memberID) {
         List<Plan> plans= planRepository.getPlansByMember_MemberId(memberID);
        List<PlanResponseDto> planResponseDtos = plans.stream().map(PlanResponseDto::new).toList();
        return planResponseDtos;
    }
}
