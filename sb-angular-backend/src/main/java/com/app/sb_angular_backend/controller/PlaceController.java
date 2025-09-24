package com.app.sb_angular_backend.controller;
import com.app.sb_angular_backend.dto.*;
import com.app.sb_angular_backend.service.PlaceService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.List;
import java.util.Map;
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
    @PreAuthorize("hasAuthority('SCOPE_read:places')")
    public ResponseEntity<List<PlaceResponse>> getNearest(@ModelAttribute NearestPlacesRequest nearestPlacesRequest) {
        return ResponseEntity.ok(this.placeService.getNearestPlaces(nearestPlacesRequest));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_add:places')")
    public ResponseEntity<PlaceResponse> addNewPlace(@RequestBody @Valid PlaceRequest placeRequest) {
        // @Valid enforces the DTO @NotNull checks
        PlaceResponse placeResponse = this.placeService.addNewPlace(placeRequest);
        return ResponseEntity.ok(placeResponse);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SCOPE_read:places')")
    public ResponseEntity<PlaceResponse> getPlace(@PathVariable UUID pathId) {
        PlaceResponse placeResponse = this.placeService.getPlaceById(pathId);
        return ResponseEntity.ok(placeResponse);
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('SCOPE_read:places')")
    public ResponseEntity<PagedResponse<PlaceResponse>> getPlaces(@ModelAttribute @Valid PlacesRequest placesRequest) {
        PagedResponse<PlaceResponse> places = this.placeService.getPlaces(placesRequest);
        return ResponseEntity.ok(places);
    }

}
