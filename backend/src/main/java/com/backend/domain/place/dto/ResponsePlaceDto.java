package com.backend.domain.place.dto;

import com.backend.domain.category.entity.Category;
import com.backend.domain.place.entity.Place;
import com.backend.domain.plan.entity.Plan;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponsePlaceDto {

    private Long id;
    private String placeName;
    private String address;
    private String gu;
    private String category;
    private String description;

    public static ResponsePlaceDto from(Place place) {
        return new ResponsePlaceDto(
                place.getId(),
                place.getPlaceName(),
                place.getAddress(),
                place.getGu(),
                place.getCategory().getName(),
                place.getDescription()
        );
    }
}
