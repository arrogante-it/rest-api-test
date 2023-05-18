package com.arroganteIT.rest.persistance;

import com.arroganteIT.rest.persistance.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employees, Long> {
    Employees findByUniqueNumber(String uniqueNumber);
}
