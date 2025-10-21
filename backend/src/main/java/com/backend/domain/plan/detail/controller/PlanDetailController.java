package com.backend.domain.plan.detail.controller;

import com.backend.domain.plan.detail.dto.PlanDetailRequestBody;
import com.backend.domain.plan.detail.dto.PlanDetailResponseBody;
import com.backend.domain.plan.detail.dto.PlanDetailsElementBody;
import com.backend.domain.plan.detail.entity.PlanDetail;
import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.detail.service.PlanDetailService;
import com.backend.domain.plan.entity.Plan;
import com.backend.global.reponse.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plan/detail")
@RequiredArgsConstructor
public class PlanDetailController {
    private final PlanDetailService planDetailService;

    @PostMapping("/add")
    public ApiResponse<PlanDetailResponseBody> addPlanDetail(
            @Valid @RequestBody PlanDetailRequestBody planDetailRequestBody
    ) {
        long memberPkId = 1;
        PlanDetail planDetail = planDetailService.addPlanDetail(planDetailRequestBody, memberPkId);

        return ApiResponse.created(
                new PlanDetailResponseBody(planDetail)
        );
    }

    @GetMapping("/{planDetailId}")
    public ApiResponse<PlanDetailsElementBody> getPlanDetail(
            @NotNull @PathVariable long planDetailId
    ) {
        long memberPkId = 3;

        PlanDetailsElementBody planDetailsElementBody = planDetailService.getPlanDetailById(planDetailId, memberPkId);

        return ApiResponse.success(
                planDetailsElementBody
        );
    }

    @GetMapping("/{planId}/list")
    public ApiResponse<List<PlanDetailsElementBody>> getAllPlanDetail(
            @NotNull @PathVariable long planId
    ) {
        long memberPkId = 1;

        List<PlanDetailsElementBody> planDetailsElementBodies = planDetailService.getPlanDetailsByPlanId(planId, memberPkId);
        return ApiResponse.success(planDetailsElementBodies);
    }

    @GetMapping("/{planId}/todaylist")
    public ApiResponse<List<PlanDetailsElementBody>> getTodayPlanDetail(
            @NotNull @PathVariable long planId
    ) {
        long memberPkId = 1;
        List<PlanDetailsElementBody> planDetailsElementBodies = planDetailService.getTodayPlanDetails(memberPkId, planId);
        return ApiResponse.success(planDetailsElementBodies);
    }


    @PatchMapping("/{planDetailId}/update")
    public ApiResponse<PlanDetailResponseBody> updatePlanDetail(
            @NotNull @PathVariable long planDetailId,
            @Valid @RequestBody PlanDetailRequestBody planDetailRequestBody
    ) {
        long memberPkId = 1;

        PlanDetailResponseBody planDetailResponseBody = planDetailService.updatePlanDetail(planDetailRequestBody, memberPkId, planDetailId);
        return ApiResponse.success(
                planDetailResponseBody
        );
    }

    @DeleteMapping("/{planDetailId}/delete")
    public ApiResponse<PlanDetailResponseBody> deletePlanDetail(
            @PathVariable long planDetailId
    ) {
        long memberPkId = 1;
        planDetailService.deletePlanDetail(planDetailId, memberPkId);
        return ApiResponse.success();
    }


}
