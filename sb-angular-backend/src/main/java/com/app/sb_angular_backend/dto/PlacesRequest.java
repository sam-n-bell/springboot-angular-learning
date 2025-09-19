package com.app.sb_angular_backend.dto;
import io.swagger.v3.oas.annotations.media.Schema;

public class PlacesRequest {
    @Schema(description = "0-Indexed Page number for pagination", defaultValue = "0", example = "0")
    private int pageNumber = 0;

    @Schema(description = "Number of results to return", defaultValue = "10", example = "10")
    private int pageSize = 10;

    public PlacesRequest() {}

    public PlacesRequest(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
