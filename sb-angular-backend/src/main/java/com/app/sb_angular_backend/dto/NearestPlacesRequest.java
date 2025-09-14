package com.app.sb_angular_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class NearestPlacesRequest {
    private double longitude;
    private double latitude;
    private double kmDistance = 5.0;
    private int numberOfResults = 5;

    @NotBlank(message="Longitude required")
    public double getLongitude() {
        return longitude;
    }
    @NotBlank(message="Latitude required")
    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setKmDistance(double kmDistance) {
        this.kmDistance = kmDistance;
    }

    public void setNumberOfResults(int numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    public double getKmDistance() {
        return kmDistance;
    }

    public int getNumberOfResults() {
        return numberOfResults;
    }
}
