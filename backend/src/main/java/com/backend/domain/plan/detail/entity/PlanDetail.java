package com.backend.domain.plan.detail.entity;

import com.backend.domain.place.entity.Place;
import com.backend.domain.plan.entity.Plan;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class PlanDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinTable(name="plan")
    @ManyToOne(fetch = FetchType.LAZY)
    private Plan plan;

    @JoinTable(name = "place")
    @ManyToOne(fetch = FetchType.LAZY)
    private Place place;

    @NotNull
    LocalDateTime startTime;

    @NotNull
    LocalDateTime endTime;

    @NotNull
    String title;

    @NotNull
    String content;



}
