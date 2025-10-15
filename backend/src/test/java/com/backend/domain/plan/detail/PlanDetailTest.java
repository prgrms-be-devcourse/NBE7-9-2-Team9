package com.backend.domain.plan.detail;

import com.backend.domain.category.entity.Category;
import com.backend.domain.category.repository.CategoryRepository;
import com.backend.domain.member.dto.request.MemberSignupRequest;
import com.backend.domain.member.entity.Member;
import com.backend.domain.member.repository.MemberRepository;
import com.backend.domain.member.service.MemberService;
import com.backend.domain.place.entity.Place;
import com.backend.domain.plan.detail.controller.PlanDetailController;
import com.backend.domain.plan.detail.service.PlanDetailService;
import com.backend.domain.plan.entity.Plan;
import com.backend.domain.plan.repository.PlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.management.Query;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PlanDetailTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanDetailService planDetailService;

    @Autowired
    private PlanDetailController planDetailController;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlanRepository planRepository;

//    @Autowired
//    private PlaceRepository placeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void prepareData() throws InterruptedException {

    }

    @Test
    @DisplayName("계획 세부 저장 테스트")
    void t1() throws Exception {
        Category category = new Category();
        category.setName("test");
        categoryRepository.save(category);

        Place place = new Place();
        place.setPlaceName("test");
        place.setCategory(category);
//        placeRepository.save(place);

//        Place place2 = placeRepository.findById(1L).orElseThrow();

        MemberSignupRequest ms = new MemberSignupRequest(
                "dummy",
                "1234",
                "mail@test.com",
                "dummy"
        );

        memberService.signup(ms);
        Plan plan = new Plan(memberRepository.findById(1L).get(), LocalDateTime.now(),LocalDateTime.now(),"test","test");
        planRepository.save(plan);

        ResultActions resultActions = mockMvc
                .perform(
                        post("/api/plan/detail/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        "planId" : "1",
                                        "placeId" : "1",
                                        "startTime" : "2020-01-01T00:00:00",
                                        "endTime" : "2020-01-02T00:00:00",
                                        "title" : "세부 작성 테스트용 타이틀",
                                        "content" : "세부 작성 테스트용 콘텐츠"
                                        }
                                        """)

                ).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(PlanDetailController.class))
                ;
    }

}
