package com.arroganteIT.rest.controller;


import com.arroganteIT.rest.persistance.entity.Employees;
import com.arroganteIT.rest.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.xml.bind.ValidationException;
import com.arroganteIT.rest.exception.ValidationException;

import java.util.List;

@RestController
@RequestMapping("${application.endpoint.employee}")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping()
    public ResponseEntity<List<Employees>> getEmployees() {
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<?> createOrUpdateEmployee(@RequestBody final Employees employee) {
        try {
            employeeService.save(employee);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(e.getViolations());
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}






















































