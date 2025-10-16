package com.backend.domain.plan.detail.entity;

import com.backend.domain.member.entity.Member;
import com.backend.domain.place.entity.Place;
import com.backend.domain.plan.detail.dto.PlanDetailRequestBody;
import com.backend.domain.plan.entity.Plan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Plan plan;

    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @NotNull
    LocalDateTime startTime;

    @NotNull
    LocalDateTime endTime;

    @NotNull
    String title;

    @NotNull
    String content;

    public PlanDetail(Plan plan, Place place, Member member, LocalDateTime startTime, LocalDateTime endTime, String title, String content) {
        this.plan = plan;
        this.place = place;
        this.member = member;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
    }

    public PlanDetail(Member member, Plan plan, Place place ,PlanDetailRequestBody planDetailRequestBody) {
        this.member = member;
        this.plan = plan;
        this.place = place;
        this.startTime = planDetailRequestBody.startTime();
        this.endTime = planDetailRequestBody.endTime();
        this.title = planDetailRequestBody.title();
        this.content = planDetailRequestBody.content();
    }

    public PlanDetail(Long id, Plan plan, Place place, Member member, LocalDateTime startTime, LocalDateTime endTime, String title, String content) {
        this.id = id;
        this.plan = plan;
        this.place = place;
        this.member = member;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.content = content;
    }
}
