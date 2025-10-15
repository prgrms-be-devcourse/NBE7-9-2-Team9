package com.backend.domain.place.controller;

import com.backend.domain.place.dto.RequestPlaceDto;
import com.backend.domain.place.dto.ResponsePlaceDto;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.service.PlaceService;
import com.backend.global.reponse.ApiResponse;
import com.backend.global.reponse.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponsePlaceDto>> getPlace(@PathVariable Long id) {
        ResponsePlaceDto data = placeService.findOnePlace(id);
        return ResponseEntity.ok(ApiResponse.success(data));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createPlace(@RequestBody RequestPlaceDto dto) {
        placeService.save(dto);
        return ResponseEntity.ok(ApiResponse.success(null,"여행지가 성공적으로 생성되었습니다."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ResponsePlaceDto>> updatePlace(
            @PathVariable Long id,
            @RequestBody RequestPlaceDto dto
    ) {
        ResponsePlaceDto updated = placeService.update(id, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "여행지가 성공적으로 수정되었습니다."));
    }



}
