package com.backend.domain.plan.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    public Plan(Long id, LocalDateTime startDate, LocalDateTime endDate, String title, String content) {
        this.id = id;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.content = content;
    }

    public Plan modifyPlan(String title, String content){
        this.title = title;
        this.content = content;
        this.modifyDate = LocalDateTime.now();
        return this;
    }


}
