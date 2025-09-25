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
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void addNewEmployee(String firstName, String lastName, String email) {
        Optional<Employee> employee = employeeRepository.findByEmail(email.toLowerCase());
        if (employee.isEmpty()) {
            employeeRepository.save(new Employee(
                    firstName, lastName, email)
            );
        } else {
            throw new ResourceAlreadyExistsException(email + " already in use");
        }
    }

    public List<EmployeeResponse> getAllEmployees() {
        return this.employeeRepository.findAll().stream().map(EmployeeResponse::from).toList();
    }

    public EmployeeResponse findEmployeeById(UUID uuid) {
        Employee employee = this.employeeRepository.findById(uuid).orElseThrow(ResourceNotFoundException::new);
        return EmployeeResponse.from(employee);
    }

    public Employee findByEmail(String email) {
        return this.employeeRepository.findByEmail(email.toLowerCase()).orElseThrow(ResourceNotFoundException::new);
    }

    public void deleteEmployeeById(UUID uuid) {
        log.info("Deleted UUID: {}", uuid);
        this.employeeRepository.deleteById(uuid);
    }

}
