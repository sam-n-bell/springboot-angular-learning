package com.app.sb_angular_backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class PlaceRequest {

    @NotNull
    @Schema(description = "WGS85 longitude (X) coordinate", example = "-97.5746")
    private double longitude;
    @NotNull
    @Schema(description = "WGS85 longitude (Y) coordinate", example = "33.5748")
    private double latitude;
    @NotNull
    @Schema(example = "Building 3")
    private String name;
    @NotNull
    @Schema(example = "Where administrative and maintenance is housed")
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
