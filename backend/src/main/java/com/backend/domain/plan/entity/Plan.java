package com.backend.domain.plan.entity;

import com.backend.domain.member.entity.Member;
import com.backend.domain.plan.dto.PlanCreateRequestBody;
import com.backend.domain.plan.dto.PlanUpdateRequestBody;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private LocalDateTime modifyDate;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<PlanMember> planMembers = new ArrayList<>();

    public Plan(Member member, LocalDateTime startDate, LocalDateTime endDate, String title, String content) {
        this.member = member;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }

    public Plan(LocalDateTime startDate, LocalDateTime endDate, String title, String content) {
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }

    public Plan(PlanCreateRequestBody planCreateRequestBody, Member member) {
        this.member = member;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.startDate = planCreateRequestBody.startDate();
        this.endDate = planCreateRequestBody.endDate();
        this.title = planCreateRequestBody.title();
        this.content = planCreateRequestBody.content();
    }

    public Plan(long planId, Member member) {
        this.id = planId;
        this.member = member;
    }

    public Plan updatePlan(PlanUpdateRequestBody planUpdateRequestBody, Member member) {
        this.member = member;
        this.title = planUpdateRequestBody.title();
        this.content = planUpdateRequestBody.content();
        this.startDate = planUpdateRequestBody.startDate();
        this.endDate = planUpdateRequestBody.endDate();
        this.modifyDate = LocalDateTime.now();
        return this;
    }


}
