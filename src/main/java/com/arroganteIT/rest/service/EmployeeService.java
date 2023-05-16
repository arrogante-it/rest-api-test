package com.arroganteIT.rest.service;

import com.arroganteIT.rest.exception.ValidationException;
import com.arroganteIT.rest.persistance.entity.Employees;
import com.arroganteIT.rest.persistance.EmployeeRepository;
import com.arroganteIT.rest.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final ValidationService validationService;

    private final EmployeeRepository employeeRepository;

    public List<Employees> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Transactional
    public void save(Employees employee) throws ValidationException {
        if (validationService.isValidEmployee(employee)) {
            Employees existingEmployee = employeeRepository.findByUniqueNumber(employee.getUniqueNumber());
            if (existingEmployee == null) {
                employeeRepository.save(employee);
            } else {
                existingEmployee = updateFields(existingEmployee, employee);
                employeeRepository.save(existingEmployee);
            }
        }
    }

    private Employees updateFields(Employees existingEmployee, Employees updatedEmployee) {
        return existingEmployee.setDepartment(updatedEmployee.getDepartment())
                .setSalary(updatedEmployee.getSalary())
                //TODO implement tasks merging instead of replacement
                .setTasks(updatedEmployee.getTasks());
    }
}






































