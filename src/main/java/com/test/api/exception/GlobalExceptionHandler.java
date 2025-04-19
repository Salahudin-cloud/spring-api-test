package com.test.api.exception;

import com.test.api.dto.WebResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<WebResponse<List<String>>> methodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());


        WebResponse<List<String>> response = WebResponse.<List<String>>builder()
                .message("failed")
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<List<String>>> responseStatusException(ResponseStatusException exception) {
        List<String> errors = new ArrayList<>();

        errors.add(exception.getReason());


        WebResponse<List<String>> response = WebResponse.<List<String>>builder()
                .message("failed")
                .errors(errors)
                .build();

        return ResponseEntity.status(exception.getStatusCode()).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<WebResponse<List<String>>> noResourceFoundException(NoResourceFoundException exception) {
        List<String> errors = new ArrayList<>();

        errors.add(exception.getMessage());


        WebResponse<List<String>> response = WebResponse.<List<String>>builder()
                .message("failed")
                .errors(errors)
                .build();

        return ResponseEntity.status(exception.getStatusCode()).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<WebResponse<String>> handleEntityNotFound(EntityNotFoundException ex) {

        WebResponse<String> response = WebResponse.<String>builder()
                .message("Resource not found")
                .errors(Collections.singletonList(ex.getMessage()))
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebResponse<String> handleIllegalArgument(IllegalArgumentException ex) {
        return WebResponse.<String>builder()
                .message("failed")
                .errors(Collections.singletonList(ex.getMessage()))
                .build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<WebResponse<List<String>>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        List<String> errors = new ArrayList<>();

        String fieldName = "";
        String requiredType = "";

        if (ex.getRequiredType() != null) {
            requiredType = ex.getRequiredType().getSimpleName();
        }

        ex.getParameter();
        if (ex.getParameter().getParameterName() != null) {
            fieldName = ex.getParameter().getParameterName();
        }

        String errorMessage = String.format("Invalid input for parameter '%s'. Expected type: %s", fieldName, requiredType);
        errors.add(errorMessage);

        WebResponse<List<String>> response = WebResponse.<List<String>>builder()
                .message("failed")
                .errors(errors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}