package com.app.sb_angular_backend.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PagedResponse<T> {
    private List<T> data;
    private int pageNumber;
    private int totalPages;
    private long totalEntities;

    public PagedResponse(Page<T> page) {
        this.data = page.getContent();
        this.pageNumber = page.getNumber();
        this.totalPages = page.getTotalPages();
        this.totalEntities = page.getTotalElements(); // returns a long
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalEntities() {
        return totalEntities;
    }

    public void setTotalEntities(long totalEntities) {
        this.totalEntities = totalEntities;
    }
}
