package com.backend.domain.plan.detail.service;

import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.repository.PlanRepository;
import com.backend.domain.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanDetailService {
    private final PlanService planService;
    private final PlanDetailRepository planDetailRepository;



}
