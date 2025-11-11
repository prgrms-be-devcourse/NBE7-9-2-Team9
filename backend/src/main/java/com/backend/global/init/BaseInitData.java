package com.backend.global.init;

import com.backend.domain.member.entity.Member;
import com.backend.domain.member.entity.Role;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class BaseInitData {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final PlanRepository planRepository;



    @Bean
    public ApplicationRunner InitData() {
        return args -> {
            if (memberRepository.count() == 0) {

                Member member1 = Member.builder()
                        .memberId("member1")
                        .password(passwordEncoder.encode("1111"))
                        .email("member1@gmail.com")
                        .nickname("사용자1")
                        .role(Role.USER)
                        .build();

                Member member2 = Member.builder()
                        .memberId("member2")
                        .password(passwordEncoder.encode("2222"))
                        .email("member2@gmail.com")
                        .nickname("사용자2")
                        .role(Role.ADMIN)
                        .build();

                Member admin = Member.builder()
                        .memberId("admin")
                        .password(passwordEncoder.encode("admin1234"))
                        .email("admin@gmail.com")
                        .nickname("관리자")
                        .role(Role.ADMIN)
                        .build();

                memberRepository.saveAll(List.of(member1, member2, admin));
                log.info("초기 member 데이터 세팅 완료: ");
            }

            if(planRepository.count() == 0) {

                Plan plan1 = Plan.builder()
                        .member(memberRepository.getMemberById(1L))
                        .createDate(LocalDateTime.now())
                        .modifyDate(LocalDateTime.now())
                        .startDate(LocalDateTime.now())
                        .endDate(LocalDateTime.now().plusDays(1L))
                        .title("초기 일정 데이터1")
                        .content("초기 일정 데이터 내용")
                        .build();
                plan1.timeSet();

                Plan plan2 = Plan.builder()
                        .member(memberRepository.getMemberById(2L))
                        .createDate(LocalDateTime.now())
                        .modifyDate(LocalDateTime.now())
                        .startDate(LocalDateTime.now().plusDays(2L))
                        .endDate(LocalDateTime.now().plusDays(4L))
                        .title("초기 일정 데이터2")
                        .content("초기 일정 데이터 내용")
                        .build();
                plan2.timeSet();

                Plan plan3 = Plan.builder()
                        .member(memberRepository.getMemberById(1L))
                        .createDate(LocalDateTime.now())
                        .modifyDate(LocalDateTime.now())
                        .startDate(LocalDateTime.now().plusDays(3L))
                        .endDate(LocalDateTime.now().plusDays(5L))
                        .title("초기 일정 데이터2")
                        .content("초기 일정 데이터 내용2")
                        .build();
                plan3.timeSet();

                planRepository.saveAll(List.of(plan1, plan2, plan3));
                log.info("초기 plan 데이터 세팅 완료: ");
            }




        };
    }
}
