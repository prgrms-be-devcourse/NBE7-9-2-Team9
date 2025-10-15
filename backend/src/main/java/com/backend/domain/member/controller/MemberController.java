package com.backend.domain.member.controller;

import com.backend.domain.member.dto.request.MemberSignupRequest;
import com.backend.domain.member.dto.response.MemberResponse;
import com.backend.domain.member.service.MemberService;
import com.backend.global.reponse.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<MemberResponse> signup(@RequestBody MemberSignupRequest request) {
        MemberResponse response = memberService.signup(request);
        return ApiResponse.success(response);
    }
}
