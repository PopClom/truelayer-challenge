package com.truelayer.challenge.controller;

import com.truelayer.challenge.dto.ErrorResponse;
import com.truelayer.challenge.exception.PokemonNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(PokemonNotFound.class)
    public ResponseEntity<ErrorResponse> handlePokemonNotFoundException(PokemonNotFound ex) {
        ErrorResponse response = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .error(ex.getMessage())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
