package com.backend.domain.member.controller;

import com.backend.domain.member.dto.request.MemberLoginRequest;
import com.backend.domain.member.dto.request.MemberSignupRequest;
import com.backend.domain.member.dto.request.MemberUpdateRequest;
import com.backend.domain.member.dto.response.MemberResponse;
import com.backend.domain.member.service.MemberService;
import com.backend.global.reponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<MemberResponse> signup(@RequestBody MemberSignupRequest request) {
        MemberResponse response = memberService.signup(request);
        return ApiResponse.success(response, "회원가입이 완료되었습니다");
    }

    @PostMapping("/login")
    public ApiResponse<MemberResponse> login(@RequestBody MemberLoginRequest request) {
        MemberResponse response = memberService.login(request);
        return ApiResponse.success(response, "로그인 성공! 환영합니다");
    }

    @GetMapping("/{memberId}")
    public ApiResponse<MemberResponse> getMember(@PathVariable String memberId) {
        MemberResponse response = memberService.getMember(memberId);
        return ApiResponse.success(response, "회원 조회 성공");
    }

    @PatchMapping("/{memberId}")
    public ApiResponse<MemberResponse> updateMember(
            @PathVariable String memberId,
            @RequestBody MemberUpdateRequest request
    ) {
        MemberResponse response = memberService.updateMember(memberId, request);
        return ApiResponse.success(response, "회원정보가 수정되었습니다");
    }

    @DeleteMapping("/{memberId}")
    public ApiResponse<MemberResponse> deleteMember(@PathVariable String memberId) {
        MemberResponse response = memberService.deleteMember(memberId);
        return ApiResponse.success(response, "회원 탈퇴가 완료되었습니다.");
    }
}
