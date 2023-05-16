package com.arroganteIT.rest.exception;

import com.arroganteIT.rest.validation.Violation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ValidationException extends Exception {

    @Getter
    private final List<Violation> violations;
}
