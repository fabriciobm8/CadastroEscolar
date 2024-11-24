package com.multiversa.escola.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // Capturar erros de validação
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>>
  handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> erros = new HashMap<>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      erros.put(error.getField(), error.getDefaultMessage());
    }
    return ResponseEntity.badRequest().body(erros);
  }

}
