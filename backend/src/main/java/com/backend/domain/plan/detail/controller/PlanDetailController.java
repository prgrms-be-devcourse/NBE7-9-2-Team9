package com.backend.domain.plan.detail.controller;

import com.backend.domain.plan.detail.dto.PlanDetailRequestBody;
import com.backend.domain.plan.detail.dto.PlanDetailResponseBody;
import com.backend.domain.plan.detail.dto.PlanDetailsElementBody;
import com.backend.domain.plan.detail.entity.PlanDetail;
import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.detail.service.PlanDetailService;
import com.backend.domain.plan.entity.Plan;
import com.backend.global.reponse.ApiResponse;
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

    @GetMapping("/{planDetailId}")
    public ResponseEntity<ApiResponse<PlanDetailResponseBody>> getPlanDetail(
            @PathVariable long planDetailId
    ){
        //TODO 추후 초대된 사용자들만 조회 될 수 있게 하기.
        String memberId = "dummy";

        PlanDetailResponseBody planDetailResponseBody = planDetailService.getPlanDetailById(planDetailId, memberId);

        return new ResponseEntity<>(
                ApiResponse.success(
                        planDetailResponseBody
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{planId}/list")
    public ResponseEntity<ApiResponse<List<PlanDetailsElementBody>>> getAllPlanDetail(
            @PathVariable long planId
    ){
        String memberId = "dummy";

        List<PlanDetailsElementBody> planDetailsElementBodies = planDetailService.getPlanDetailsByPlanId(planId,memberId);
        return new ResponseEntity<>(
                ApiResponse.success(
                        planDetailsElementBodies
                ),
                HttpStatus.OK
        );
    }
}
