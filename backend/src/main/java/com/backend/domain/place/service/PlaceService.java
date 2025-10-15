package com.backend.domain.place.service;

import com.backend.domain.place.dto.ResponsePlaceDto;
import com.backend.domain.place.repository.PlaceRepository;
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

}
