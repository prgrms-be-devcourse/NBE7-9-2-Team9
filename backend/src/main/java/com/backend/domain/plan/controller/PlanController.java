package com.backend.domain.plan.controller;

import com.backend.domain.plan.dto.PlanCreateRequestDto;
import com.backend.domain.plan.dto.PlanResponseDto;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.service.PlanService;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ApiResponse;
import com.backend.global.reponse.ErrorCode;
import com.backend.global.reponse.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController {
    private final PlanService planService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PlanResponseDto>> create(
            @Valid @RequestBody PlanCreateRequestDto planCreateRequestDto
            ) {
        Plan plan = planService.CreatePlan(planCreateRequestDto);
        PlanResponseDto planResponseDto = new PlanResponseDto(plan);
        return new ResponseEntity<>(ApiResponse.success(planResponseDto), HttpStatus.OK);
    }
}
