package com.app.sb_angular_backend.dto;

import jakarta.validation.constraints.NotNull;

public class PlaceRequest {

    @NotNull
    private double longitude;
    @NotNull
    private double latitude;
    @NotNull
    private String name;
    @NotNull
    private String description;

    public PlaceRequest () {};

    public PlaceRequest(double longitude, double latitude, String name, String description) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = name;
        this.description = description;
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
}
