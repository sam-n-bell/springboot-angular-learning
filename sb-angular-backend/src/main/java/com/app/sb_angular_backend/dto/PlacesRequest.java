package com.app.sb_angular_backend.dto;

public class PlacesRequest {

    private int pageNumber = 0;

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
