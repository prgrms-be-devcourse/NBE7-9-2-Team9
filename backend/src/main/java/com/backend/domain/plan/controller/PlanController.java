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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController {
    private final PlanService planService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PlanResponseDto>> create(
            @Valid @RequestBody PlanCreateRequestDto planCreateRequestDto
            ) {

        // TODO : JWT 토큰에서 멤버 아이디 정보 가져오기
        String dummyMemberID = "dummy";

        Plan plan = planService.CreatePlan(planCreateRequestDto, dummyMemberID);
        PlanResponseDto planResponseDto = new PlanResponseDto(plan);
        return new ResponseEntity<>(ApiResponse.success(planResponseDto), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<PlanResponseDto>>> getList(){
        //TODO 페이징 처리 적용하기, 일단은 전체 목록 조회
        //TODO JWT 토큰에서 멤버 아이디 정보 가져오기
        String dummyMemberID = "dummy";

        List<PlanResponseDto> plans = planService.getPlanList(dummyMemberID);
        return new ResponseEntity<>(ApiResponse.success(plans), HttpStatus.OK);

    }
}
