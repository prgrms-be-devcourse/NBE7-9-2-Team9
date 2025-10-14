package com.backend.domain.plan.controller;

import com.backend.domain.plan.dto.PlanCreateRequestDto;
import com.backend.domain.plan.entity.Plan;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plan")
public class PlanController {

    @PostMapping("/create")
    public Plan create(
            @Valid @RequestBody PlanCreateRequestDto planCreateRequestDto
            ) {


        return new Plan();
    }
}
