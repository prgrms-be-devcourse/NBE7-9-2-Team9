package com.backend.domain.plan.detail.controller;

import com.backend.domain.plan.detail.dto.PlanDetailRequestBody;
import com.backend.domain.plan.detail.dto.PlanDetailResponseBody;
import com.backend.domain.plan.detail.entity.PlanDetail;
import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.detail.service.PlanDetailService;
import com.backend.domain.plan.entity.Plan;
import com.backend.global.reponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plan/detail")
@RequiredArgsConstructor
public class PlanDetailController {
    private final PlanDetailService planDetailService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<PlanDetailResponseBody>> addPlanDetail(
            @RequestBody PlanDetailRequestBody planDetailRequestBody
    ) {
        String memberId = "dummy";
        PlanDetail planDetail = planDetailService.addPlanDetail(planDetailRequestBody, memberId);

        return new ResponseEntity<>(
                ApiResponse.success(
                        new PlanDetailResponseBody(planDetail)
                ),
                HttpStatus.OK
        );
    }
}
