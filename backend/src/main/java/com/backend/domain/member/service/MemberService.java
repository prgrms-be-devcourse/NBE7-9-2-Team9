package com.backend.domain.member.service;

import com.backend.domain.member.dto.request.MemberSignupRequest;
import com.backend.domain.member.dto.response.MemberResponse;
import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import lombok.RequiredArgsConstructor;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//TODO: transactional 어디에 붙이는 게 좋을지
public class MemberService {

    private final MemberRepository memberRepository;
    //private final PasswordEncoder passwordEncoder;

    //TODO: 반환값 id OR member 객체
    public MemberResponse signup(MemberSignupRequest request) {
        validateDuplicate(request);

        // 비밀번호 암호화 TODO: 암호화 적용
        // String encodedPassword = passwordEncoder.encode(request.password());
        String encodedPassword = request.password();


        Member member = request.toEntity(encodedPassword);
        memberRepository.save(member);

        return MemberResponse.from(member);
    }

    private void validateDuplicate(MemberSignupRequest request) {

        if (memberRepository.existsByMemberId(request.memberId())) {
            throw new BusinessException(ErrorCode.DUPLICATE_MEMBER_ID);
        }

        if (memberRepository.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        if (memberRepository.existsByNickname(request.nickname())) {
            throw new BusinessException(ErrorCode.DUPLICATE_NICKNAME);
        }
    }
}
