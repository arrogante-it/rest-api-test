package com.arroganteIT.rest.persistance.repository;

import com.arroganteIT.rest.persistance.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
