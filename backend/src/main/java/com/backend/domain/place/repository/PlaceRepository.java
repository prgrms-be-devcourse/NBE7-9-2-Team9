// PlaceRepository.java
package com.backend.domain.place.repository;

import com.backend.domain.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    boolean existsByPlaceNameAndAddress(String placeName, String address);
}