package com.app.sb_angular_backend.controller;

import com.app.sb_angular_backend.dto.EmployeeRequest;
import com.app.sb_angular_backend.dto.EmployeeResponse;
import com.app.sb_angular_backend.repository.EmployeeRepository;
import com.app.sb_angular_backend.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Employee controller with Auth0 permissions-based authorization.
 * 
 * Auth0 Permissions used:
 * - read:employees - View employee data
 * - write:employees - Create new employees
 * - update:employees - Modify existing employees  
 * - delete:employees - Remove employees
 * 
 * These permissions should be defined in your Auth0 dashboard under APIs > Permissions.
 * Users need to be granted these permissions through Auth0 roles or direct assignment.
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository){
        this.employeeService = employeeService;
    }

    // Get all employees - requires 'read:employees' permission
    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_read:employees')")
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(this.employeeService.getAllEmployees());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_add:employees')")
    public ResponseEntity<Void> addNewEmployee(@RequestBody EmployeeRequest request) {
        // @RequestBody binds payload to DTO
        this.employeeService.addNewEmployee(request.getFirstName(), request.getLastName(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SCOPE_read:employees')")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable UUID uuid) {
        return ResponseEntity.ok(this.employeeService.findEmployeeById(uuid));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('SCOPE_delete:employees')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID uuid) {
        this.employeeService.deleteEmployeeById(uuid);
        return ResponseEntity.noContent().build();
    }
}
