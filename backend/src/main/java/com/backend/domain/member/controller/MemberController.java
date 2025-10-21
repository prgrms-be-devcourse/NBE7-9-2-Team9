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

    /*@GetMapping("/me")
    public ApiResponse<MemberResponse> getMyInfo(@AuthenticationPrincipal CustomUserDetails user) {
        MemberResponse response = memberService.getMember(user.getMemberId());
        return ApiResponse.success(response, "회원 조회 성공");
    }*/

    @PostMapping("/signup")
    public ApiResponse<MemberResponse> signup(@RequestBody MemberSignupRequest request) {
        MemberResponse response = memberService.signup(request);
        return ApiResponse.success(response, "회원가입이 완료되었습니다");
    }

    @GetMapping("/{memberId}")
    public ApiResponse<MemberResponse> getMember(@PathVariable Long memberPk) {
        MemberResponse response = memberService.getMember(memberPk);
        return ApiResponse.success(response, "회원 조회 성공");
    }

    @PatchMapping("/{memberId}")
    public ApiResponse<MemberResponse> updateMember(
            @PathVariable Long memberPk,
            @RequestBody MemberUpdateRequest request
    ) {
        MemberResponse response = memberService.updateMember(memberPk, request);
        return ApiResponse.success(response, "회원정보가 수정되었습니다");
    }

    @DeleteMapping("/{memberId}")
    public ApiResponse<MemberResponse> deleteMember(@PathVariable Long memberPk) {
        MemberResponse response = memberService.deleteMember(memberPk);
        return ApiResponse.success(response, "회원 탈퇴가 완료되었습니다.");
    }
}
