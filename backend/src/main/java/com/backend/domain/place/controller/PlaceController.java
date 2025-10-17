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

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ResponsePlaceDto>> getPlacesByCategoryId(@PathVariable int categoryId) {
        List<ResponsePlaceDto> data = placeService.findPlacesByCategoryId(categoryId);
        return ApiResponse.success(data);
    }

    @GetMapping("/{id}")
    public ApiResponse<ResponsePlaceDto> getPlace(@PathVariable Long id) {
        ResponsePlaceDto data = placeService.findOnePlace(id);
        return ApiResponse.success(data);
    }

    @PostMapping
    public ApiResponse<Void> createPlace(@RequestBody RequestPlaceDto dto) {
        placeService.save(dto);
        return ApiResponse.success(null,"여행지가 성공적으로 생성되었습니다.");
    }

    @PutMapping("/{id}")
    public ApiResponse<ResponsePlaceDto> updatePlace(
            @PathVariable Long id,
            @RequestBody RequestPlaceDto dto
    ) {
        ResponsePlaceDto updated = placeService.update(id, dto);
        return ApiResponse.success(updated, "여행지가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePlace(@PathVariable Long id) {
        placeService.delete(id);
        return ApiResponse.success(null, "여행지가 삭제되었습니다.");
    }

}
