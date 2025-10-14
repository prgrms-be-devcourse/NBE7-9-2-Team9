package com.backend.domain.plan.service;

import com.backend.domain.plan.controller.PlanController;
import com.backend.domain.plan.dto.PlanCreateRequestDto;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    public Plan CreatePlan(PlanCreateRequestDto planCreateRequestDto) {
        Plan plan = new Plan(planCreateRequestDto);
        return planRepository.save(plan);
    }


}
