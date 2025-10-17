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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private PlaceRepository placeRepository;
    private CategoryRepository categoryRepository;

    public Place findPlaceById(Long id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PLACE));
    }

    public List<ResponsePlaceDto> findPlacesByCategoryId(int categoryId) {
        return placeRepository.findByCategoryId(categoryId)
                .stream()
                .map(ResponsePlaceDto::from)
                .collect(Collectors.toList());
    }

    public ResponsePlaceDto findOnePlace(Long id) {
        return ResponsePlaceDto.from(findPlaceById(id));
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
        Place place = findPlaceById(id);
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
        placeRepository.delete(findPlaceById(id));
    }

}
