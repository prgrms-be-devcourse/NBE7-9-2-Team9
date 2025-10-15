package com.backend.domain.plan.entity;

import com.backend.domain.member.entity.Member;
import com.backend.domain.plan.dto.PlanCreateRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name="Member")
    @JoinColumn(name="id")
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

    public Plan(LocalDateTime startDate, LocalDateTime endDate, String title, String content) {
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }

    public Plan(PlanCreateRequestDto planCreateRequestDto, Member member) {
        this.member = member;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.startDate = planCreateRequestDto.startDate();
        this.endDate = planCreateRequestDto.endDate();
        this.title = planCreateRequestDto.title();
        this.content = planCreateRequestDto.content();
    }

    public Plan modifyPlan(String title, String content){
        this.title = title;
        this.content = content;
        this.modifyDate = LocalDateTime.now();
        return this;
    }


}
