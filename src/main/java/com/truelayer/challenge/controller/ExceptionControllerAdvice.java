package com.truelayer.challenge.controller;

import com.truelayer.challenge.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
        ErrorResponse response = ErrorResponse.builder()
                .status(ex.getStatusCode().value())
                .error(ex.getReason())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}