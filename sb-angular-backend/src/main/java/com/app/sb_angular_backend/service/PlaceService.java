package com.app.sb_angular_backend.service;


import com.app.sb_angular_backend.dto.*;
import com.app.sb_angular_backend.exception.ResourceNotFoundException;
import com.app.sb_angular_backend.model.Place;
import com.app.sb_angular_backend.repository.PlaceRepository;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class PlaceService {

    @Autowired
    private GeometryFactory geometryFactory;

    private static final Logger log = LoggerFactory.getLogger(PlaceService.class);
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public PlaceResponse addNewPlace(PlaceRequest request) {
        Point point = geometryFactory.createPoint(new Coordinate(request.getLongitude(), request.getLatitude()));
        point.setSRID(4326);

        Place place = new Place(request.getName(), request.getDescription(), point);
        placeRepository.save(place);
        placeRepository.flush();

        return PlaceResponse.from(place);
    }

    public List<PlaceResponse> getNearestPlaces(NearestPlacesRequest nearestPlacesRequest) {
        return this.placeRepository.findNearestPlaces(
                nearestPlacesRequest.getLongitude(),
                nearestPlacesRequest.getLatitude(),
                nearestPlacesRequest.getKmDistance(),
                nearestPlacesRequest.getNumberOfResults()
        ).stream().map(PlaceResponse::from).toList();
    }

    public PlaceResponse getPlaceById(UUID pathId) {
        Place placeResponse = this.placeRepository.findById(pathId).orElseThrow(() -> new ResourceNotFoundException(pathId + " not found"));
        return PlaceResponse.from(placeResponse);
    }

    public PagedResponse<PlaceResponse> getPlaces(PlacesRequest placesRequest) {
        Sort sort = Sort.by("createdAt");
        Pageable pageable = PageRequest.of(placesRequest.getPageNumber(), placesRequest.getPageSize(), sort);
        Page<Place> placePage = placeRepository.findAll(pageable);
        // diamond <> is a shortcut to not repeat PagedResponse<PlaceResponse> twice
        return new PagedResponse<>(placePage.map(PlaceResponse::from));
    }
}
