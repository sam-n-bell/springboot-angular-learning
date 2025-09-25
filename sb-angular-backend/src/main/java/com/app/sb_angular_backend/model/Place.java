package com.app.sb_angular_backend.model;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;
import org.locationtech.jts.geom.Point;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity // Marks class as a JPA entity that maps to a db table
@Table(name="place", schema = "app")
public class Place {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "place_id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID placeId;

    @Column(name = "name", nullable = false)
    @Convert(converter = LowerCaseStringConverter.class)
    private String name;

    @Column(name = "description", nullable = false)
    @Convert(converter = LowerCaseStringConverter.class)
    private String description;

    @Column(name = "created", insertable = false, columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime createdAt;

    @Column(name = "location", nullable = false, columnDefinition = "geometry(Point,4326)")
    private Point location;

    @ManyToOne()
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee addedByEmployee;

    public Place(){}

    public Place(String name, String description, Point location, Employee addedByEmployee) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.addedByEmployee = addedByEmployee;
    }

    public UUID getPlaceId() {
        return placeId;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreated(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Employee getAddedByEmployee() {
        return addedByEmployee;
    }

    public void setAddedByEmployee(Employee addedByEmployee) {
        this.addedByEmployee = addedByEmployee;
    }

}
