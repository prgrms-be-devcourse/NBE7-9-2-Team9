package com.backend.domain.place.controller;

import com.backend.domain.place.dto.ResponsePlaceDto;
import com.backend.domain.place.service.PlaceService;
import com.backend.global.reponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/place")
public class PlaceController {

    private final PlaceService placeService;
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResponsePlaceDto>>> getAllPlace() {
        List<ResponsePlaceDto> data = placeService.findAllPlace();
        return ResponseEntity.ok(ApiResponse.success(data));
    }

}
