package com.backend.domain.plan.entity;

import com.backend.domain.member.entity.Member;
import com.backend.domain.plan.dto.PlanCreateRequestBody;
import com.backend.domain.plan.dto.PlanUpdateRequestBody;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
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

    public Plan updatePlan(PlanUpdateRequestBody planUpdateRequestBody, Member member) {
        this.member = member;
        this.title = planUpdateRequestBody.title();
        this.content = planUpdateRequestBody.content();
        this.startDate = planUpdateRequestBody.startDate();
        this.endDate = planUpdateRequestBody.endDate();
        this.modifyDate = LocalDateTime.now();
        timeSet();
        return this;
    }

    public void timeSet() {
        this.startDate = LocalDateTime.of(this.startDate.getYear(), this.startDate.getMonth(), this.startDate.getDayOfMonth(), 0, 0, 0);
        this.endDate = LocalDateTime.of(this.endDate.getYear(),this.endDate.getMonth(),this.endDate.getDayOfMonth(), 23, 59, 59);
    }


}
