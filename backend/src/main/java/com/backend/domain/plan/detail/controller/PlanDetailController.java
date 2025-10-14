package com.backend.domain.plan.detail.controller;

import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.detail.service.PlanDetailService;
import com.backend.domain.plan.entity.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plan/detail")
@RequiredArgsConstructor
public class PlanDetailController {
    private final PlanDetailService planDetailService;


}
