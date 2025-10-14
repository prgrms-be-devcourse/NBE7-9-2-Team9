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

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final MemberRepository memberRepository; // 회원 정보 넣기 위한 임시 회원 레포지토리
    // TODO 회원 서비스 기반 처리 하기, JWT에서 멤버 ID 식별자 사용하면 더 편할것 같은데 보안상의 문제는 없는지?

    public Plan CreatePlan(PlanCreateRequestDto planCreateRequestDto,String memberId) {
        if(planCreateRequestDto.startDate().isAfter(planCreateRequestDto.endDate())){
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }

        // TODO Member 클래스 객체 가져오는거 실제로 적용하기
        Member member = new Member(1L,null,null,null,null,null);
//        Member member= memberRepository.findByMemberId(memberId);
        Plan plan = new Plan(planCreateRequestDto, member);
        return planRepository.save(plan);
    }


    public List<PlanResponseDto> getPlanList(String memberID) {
//        Member member = memberRepository.findByMemberId(memberID);
        List<PlanResponseDto> plans = planRepository.getPlanList(memberID);

        return plans;
    }
}
