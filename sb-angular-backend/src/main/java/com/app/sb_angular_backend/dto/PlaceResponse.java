package com.app.sb_angular_backend.dto;

import com.app.sb_angular_backend.model.Employee;
import com.app.sb_angular_backend.model.Place;

import java.time.OffsetDateTime;
import java.util.UUID;

public class PlaceResponse {

    private double longitude;
    private double latitude;
    private String name;
    private String description;
    private UUID placeId;

    private OffsetDateTime createdAt;


    public PlaceResponse(UUID placeId, double longitude, double latitude, String name, String description, OffsetDateTime createdAt) {
        this.placeId = placeId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getPlaceId() {
        return placeId;
    }

    public void setPlaceId(UUID placeId) {
        this.placeId = placeId;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static PlaceResponse from(Place place) {
        return new PlaceResponse(
                place.getPlaceId(),
                place.getLocation().getX(),
                place.getLocation().getY(),
                place.getName(),
                place.getDescription(),
                place.getCreatedAt()
        );
    }


}
