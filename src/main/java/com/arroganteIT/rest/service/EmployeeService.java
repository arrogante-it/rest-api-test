package com.arroganteIT.rest.service;

import com.arroganteIT.rest.persistance.entity.Employee;
import com.arroganteIT.rest.persistance.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return List.of(new Employee().setId(123L));
    }
}
