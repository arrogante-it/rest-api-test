package com.arroganteIT.rest.validation;

import com.arroganteIT.rest.exception.ValidationException;
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
public class ValidationService<T> {

    private final Validator validator;

    public boolean isValid(T t) throws ValidationException {
        Set<ConstraintViolation<T>> constraintViolation =
                validator.validate(t);

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







































