package com.arroganteIT.rest.validation;

import com.arroganteIT.rest.exception.ValidationException;
import com.arroganteIT.rest.persistance.entity.Employees;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Validator;

import javax.validation.ConstraintViolation;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final Validator validator;

    public boolean isValidEmployee(Employees employee) throws ValidationException {
        Set<ConstraintViolation<Employees>> constraintViolation =
                validator.validate(employee);

        if (CollectionUtils.isNotEmpty(constraintViolation)) {
            throw new ValidationException(buildViolationsList(constraintViolation));
        }

        return true;
    }

    private <T> List<Violation> buildViolationsList(Set<ConstraintViolation<T>> constraintViolations) {
        return constraintViolations.stream()
                .map(violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .collect(Collectors.toList());
    }
}







































