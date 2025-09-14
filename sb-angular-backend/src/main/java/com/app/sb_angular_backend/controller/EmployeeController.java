package com.app.sb_angular_backend.controller;

import com.app.sb_angular_backend.dto.EmployeeRequest;
import com.app.sb_angular_backend.dto.EmployeeResponse;
import com.app.sb_angular_backend.repository.EmployeeRepository;
import com.app.sb_angular_backend.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository){
        this.employeeService = employeeService;
    }

    // get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(this.employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<Void> addNewEmployee(@RequestBody EmployeeRequest request) {
        // @RequestBody binds payload to DTO
        this.employeeService.addNewEmployee(request.getFirstName(), request.getLastName(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<EmployeeResponse> getEmployee(@PathVariable UUID uuid) {
        return ResponseEntity.ok(this.employeeService.findEmployeeById(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID uuid) {
        this.employeeService.deleteEmployeeById(uuid);
        return ResponseEntity.noContent().build();
    }
}
