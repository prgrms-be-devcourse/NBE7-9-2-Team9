package com.backend.domain.plan.controller;

import com.backend.domain.plan.dto.*;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.service.PlanMemberService;
import com.backend.domain.plan.service.PlanService;
import com.backend.global.reponse.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private final PlanMemberService planMemberService;

    @PostMapping("/create")
    public ApiResponse<PlanResponseBody> create(
            @Valid @RequestBody PlanCreateRequestBody planCreateRequestBody
    ) {

        // TODO : JWT 토큰에서 멤버 아이디 정보 가져오기
        long memberPkId = 1;

        Plan plan = planService.createPlan(planCreateRequestBody, memberPkId);
        PlanResponseBody planResponseBody = new PlanResponseBody(plan);
        return ApiResponse.created(planResponseBody);
    }

    @GetMapping("/list")
    public ApiResponse<List<PlanResponseBody>> getList() {
        //TODO 페이징 처리 적용하기, 일단은 전체 목록 조회
        //TODO JWT 토큰에서 멤버 아이디 정보 가져오기
        long memberPkId = 1;

        List<PlanResponseBody> plans = planService.getPlanList(memberPkId);
        return ApiResponse.success(plans);

    }

    @PatchMapping("/update/{planId}")
    public ApiResponse<PlanResponseBody> updatePlan(
            @Valid @RequestBody PlanUpdateRequestBody planUpdateRequestBody,
            @PathVariable long planId
    ) {
        //TODO JWT 토큰에서 멤버 아이디 정보 가져오기
        long memberPkId = 1;

        PlanResponseBody planResponseBody = planService.updatePlan(planId, planUpdateRequestBody, memberPkId);

        return ApiResponse.success(planResponseBody);
    }

    @GetMapping("/{planId}")
    public ApiResponse<PlanResponseBody> getPlan(
         @NotNull @PathVariable long planId
    ) {
        PlanResponseBody planResponseBody = planService.getPlanResponseBodyById(planId);
        return ApiResponse.success(planResponseBody);
    }

    @DeleteMapping("/delete/{planId}")
    public ResponseEntity deletePlan(
           @NotNull @PathVariable long planId
    ) {
        //TODO JWT 토큰에서 멤버 아이디 정보 가져오기
        long memberPkId = 1;

        planService.deletePlanById(planId, memberPkId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/member/invite")
    public ApiResponse<PlanMemberResponseBody> inviteMember(
            @Valid @RequestBody PlanMemberAddRequestBody memberRequestBody
    ) {
        long memberPkId = 1;
        PlanMemberResponseBody planMemberResponseBody = planMemberService.invitePlanMember(memberRequestBody, memberPkId);
        return ApiResponse.success(planMemberResponseBody);
    }

    @GetMapping("/member/mylist")
    public ApiResponse<List<PlanMemberMyResponseBody>> getMyPlanMember(){
        long memberPkId = 2;
        return ApiResponse.success(planMemberService.myInvitedPlanList(memberPkId));
    }

    @PatchMapping("/member/accept")
    public ApiResponse<PlanMemberResponseBody> acceptMember(
           @Valid @RequestBody PlanMemberAnswerRequestBody memberAnswerRequestBody
    ){
        long memberPkId = 2;
        PlanMemberResponseBody planMemberResponseBody = planMemberService.acceptInvitePlanMember(memberAnswerRequestBody, memberPkId);

        return ApiResponse.success(planMemberResponseBody);
    }

    @PatchMapping("/member/deny")
    public ApiResponse<PlanMemberResponseBody> denyMember(
          @Valid  @RequestBody PlanMemberAnswerRequestBody memberAnswerRequestBody
    ){
        long memberPkId = 2;
        PlanMemberResponseBody planMemberResponseBody = planMemberService.denyInvitePlanMember(memberAnswerRequestBody, memberPkId);

        return ApiResponse.success(planMemberResponseBody);
    }
}
