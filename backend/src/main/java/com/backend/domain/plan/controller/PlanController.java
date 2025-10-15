package com.backend.domain.plan.controller;

import com.backend.domain.plan.dto.PlanCreateRequestBody;
import com.backend.domain.plan.dto.PlanResponseBody;
import com.backend.domain.plan.dto.PlanUpdateRequestBody;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.service.PlanService;
import com.backend.global.reponse.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController {
    private final PlanService planService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PlanResponseBody>> create(
            @Valid @RequestBody PlanCreateRequestBody planCreateRequestBody
            ) {

        // TODO : JWT 토큰에서 멤버 아이디 정보 가져오기
        String memberId = "dummy";

        Plan plan = planService.createPlan(planCreateRequestBody, memberId);
        PlanResponseBody planResponseBody = new PlanResponseBody(plan);
        return new ResponseEntity<>(ApiResponse.success(planResponseBody), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<PlanResponseBody>>> getList(){
        //TODO 페이징 처리 적용하기, 일단은 전체 목록 조회
        //TODO JWT 토큰에서 멤버 아이디 정보 가져오기
        String memberId = "dummy";

        List<PlanResponseBody> plans = planService.getPlanList(memberId);
        return new ResponseEntity<>(ApiResponse.success(plans), HttpStatus.OK);

    }

    @PatchMapping("/update/{planId}")
    public ResponseEntity<ApiResponse<PlanResponseBody>> updatePlan(
            @Valid @RequestBody PlanUpdateRequestBody planUpdateRequestBody,
            @PathVariable long planId
    ){
        //TODO JWT 토큰에서 멤버 아이디 정보 가져오기
        String memberId = "dummy";

        PlanResponseBody planResponseBody = planService.updatePlan(planId,planUpdateRequestBody,memberId);

        return new ResponseEntity<>(ApiResponse.success(planResponseBody), HttpStatus.OK);
    }

    @GetMapping("/{planId}")
    public ResponseEntity<ApiResponse<PlanResponseBody>> getPlan(
            @PathVariable long planId
    ){
        PlanResponseBody planResponseBody = planService.getPlanById(planId);
        return new ResponseEntity<>(ApiResponse.success(planResponseBody), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{planId}")
    public ResponseEntity<ApiResponse> deletePlan(
            @PathVariable long planId
    ){
        //TODO JWT 토큰에서 멤버 아이디 정보 가져오기
        String memberId = "dummy";

        planService.deletePlanById(planId,memberId);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.OK);
    }
}
