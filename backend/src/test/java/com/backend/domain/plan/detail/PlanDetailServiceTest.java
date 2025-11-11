package com.backend.domain.plan.detail;

import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.plan.detail.repository.PlanDetailRepository;
import com.backend.domain.plan.detail.service.PlanDetailService;
import com.backend.domain.plan.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("dev")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class PlanDetailServiceTest {
    @Autowired
    private PlanDetailService planDetailService;

    @Autowired
    private PlanDetailRepository planDetailRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void t1(){}
}
