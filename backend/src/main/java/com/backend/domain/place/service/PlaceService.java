package com.backend.domain.place.service;

import com.backend.domain.place.dto.ResponsePlaceDto;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.repository.PlaceRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<ResponsePlaceDto> findAllPlace() {
        return placeRepository.findAll()
                .stream()
                .map(ResponsePlaceDto::from)
                .toList();
    }

    public ResponsePlaceDto findOnePlace(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        return ResponsePlaceDto.from(place);
    }
}
