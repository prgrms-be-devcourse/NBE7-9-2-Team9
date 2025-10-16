package com.backend.domain.place.service;

import com.backend.domain.category.entity.Category;
import com.backend.domain.category.repository.CategoryRepository;
import com.backend.domain.place.dto.RequestPlaceDto;
import com.backend.domain.place.dto.ResponsePlaceDto;
import com.backend.domain.place.entity.Place;
import com.backend.domain.place.repository.PlaceRepository;
import com.backend.global.exception.BusinessException;
import com.backend.global.reponse.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Builder
public class PlaceService {

    private PlaceRepository placeRepository;
    private CategoryRepository categoryRepository;

    public PlaceService(PlaceRepository placeRepository, CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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

    public void save(RequestPlaceDto dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_CATEGORY));

        Place place = Place.builder()
                .placeName(dto.placeName())
                .address(dto.address())
                .gu(dto.gu())
                .category(category)
                .description(dto.description())
                .build();

        placeRepository.save(place);
    }

    @Transactional
    public ResponsePlaceDto update(Long id, RequestPlaceDto dto) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        place.update(
                dto.placeName(),
                dto.address(),
                dto.gu(),
                dto.description()
        );

        return ResponsePlaceDto.from(place);
    }

    @Transactional
    public void delete(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));

        placeRepository.delete(place);
    }

}
