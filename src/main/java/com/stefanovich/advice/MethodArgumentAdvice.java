package com.stefanovich.advice;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class MethodArgumentAdvice extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("result", false);
        Map<String, String> errors = new LinkedHashMap<>();
        for (ObjectError error : allErrors) {
            FieldError fieldError = (FieldError) error;
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        result.put("errors", errors);

        return ResponseEntity.ok(result);
    }
}
