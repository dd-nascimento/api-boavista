package com.david.api_boavista.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarUsuarioNaoEncontrado(
        UsuarioNaoEncontradoException exception) {

    ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            exception.getMessage()
    );

    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(error);
    }
}
