package com.app.sb_angular_backend.controller;
import com.app.sb_angular_backend.dto.*;
import com.app.sb_angular_backend.exception.ResourceNotFoundException;
import com.app.sb_angular_backend.service.PlaceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/place")
// Validated will enable restraints for non-DTO-enforced params
@Validated
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService){
        this.placeService = placeService;
    }

    @GetMapping("/nearest")
    public ResponseEntity<List<PlaceResponse>> getNearest(@ModelAttribute NearestPlacesRequest nearestPlacesRequest) {
        return ResponseEntity.ok(this.placeService.getNearestPlaces(nearestPlacesRequest));
    }

    @PostMapping
    public ResponseEntity<PlaceResponse> addNewPlace(@RequestBody @Valid PlaceRequest placeRequest) {
        // @Valid enforces the DTO @NotNull checks
        PlaceResponse placeResponse = this.placeService.addNewPlace(placeRequest);
        return ResponseEntity.ok(placeResponse);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PlaceResponse> getPlace(@PathVariable UUID pathId) {
        PlaceResponse placeResponse = this.placeService.getPlaceById(pathId);
        return ResponseEntity.ok(placeResponse);
    }

    @GetMapping()
    public ResponseEntity<PagedResponse<PlaceResponse>> getPlaces(@ModelAttribute @Valid PlacesRequest placesRequest) {
        PagedResponse<PlaceResponse> places = this.placeService.getPlaces(placesRequest);
        return ResponseEntity.ok(places);
    }

}
