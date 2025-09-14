package com.app.sb_angular_backend.model;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity // Marks class as a JPA entity that maps to a db table
@Table(name="employee", schema = "app")
public class Employee {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "employee_id", updatable = false, nullable = false)
    private UUID employeeId;

    @Column(name = "first_name", nullable = false)
    @Convert(converter = LowerCaseStringConverter.class)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Convert(converter = LowerCaseStringConverter.class)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Convert(converter = LowerCaseStringConverter.class)
    private String email;

    public Employee(){}

    public Employee(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

}
