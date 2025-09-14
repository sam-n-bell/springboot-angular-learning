package com.app.sb_angular_backend.service;

import com.app.sb_angular_backend.dto.EmployeeResponse;
import com.app.sb_angular_backend.exception.ResourceAlreadyExistsException;
import com.app.sb_angular_backend.exception.ResourceNotFoundException;
import com.app.sb_angular_backend.model.Employee;
import com.app.sb_angular_backend.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addNewEmployee(String firstName, String lastName, String email) {
        Employee employee = employeeRepository.findByEmail(email.toLowerCase());
        if (employee != null) {
            throw new ResourceAlreadyExistsException("This email already in use.");
        }
        employeeRepository.save(new Employee(
                firstName, lastName, email)
        );
    }

    public List<EmployeeResponse> getAllEmployees() {
        return this.employeeRepository.findAll().stream().map(EmployeeResponse::from).toList();
    }

    public EmployeeResponse findEmployeeById(UUID uuid) {
        Employee employee = this.employeeRepository.findById(uuid).orElseThrow(ResourceNotFoundException::new);
        return EmployeeResponse.from(employee);
    }

    public void deleteEmployeeById(UUID uuid) {
        log.info("Deleted UUID: {}", uuid);
        this.employeeRepository.deleteById(uuid);
    }

}
