package com.multiversa.escola.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

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

  // Capturar erros de usuário não encontrado
  @ExceptionHandler(IdNaoEncontradoException.class)
  public ResponseEntity<String>
  handleIdNaoEncontradoException(IdNaoEncontradoException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  //Capturar erro de email ja cadastrado
  @ExceptionHandler(EmailJaCadastradoException.class)
  public ResponseEntity<String> handleEmailJaCadastradoException(EmailJaCadastradoException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  //Capturar erro de lista vazia (getAll)
  @ExceptionHandler(ListaVaziaException.class)
  public ResponseEntity<String> handleListaVaziaException(ListaVaziaException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  //Capturar Aluno já pertencendo a uma turma
  @ExceptionHandler(AlunoExistenteNaTurmaException.class)
  public ResponseEntity<String> handleAlunoExisteNaTurmaException(
      AlunoExistenteNaTurmaException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  // Capturar outras exceções genéricas
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    return
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado: " + ex.getMessage());
  }

}
