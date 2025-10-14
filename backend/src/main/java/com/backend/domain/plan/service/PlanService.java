package com.backend.domain.plan.service;

import com.backend.domain.plan.controller.PlanController;
import com.backend.domain.plan.dto.PlanCreateRequestDto;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.repository.PlanRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    public Plan CreatePlan(PlanCreateRequestDto planCreateRequestDto) {
        if(planCreateRequestDto.startDate().isAfter(planCreateRequestDto.endDate())){
            throw new BusinessException(ErrorCode.NOT_VALID_DATE);
        }

        Plan plan = new Plan(planCreateRequestDto);
        return planRepository.save(plan);
    }


}
