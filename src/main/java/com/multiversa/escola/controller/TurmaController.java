package com.multiversa.escola.controller;

import com.multiversa.escola.model.Turma;
import com.multiversa.escola.model.TurmaDTO;
import com.multiversa.escola.service.TurmaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turma")
public class TurmaController {

  @Autowired
  TurmaService turmaService;

  @PostMapping
  public ResponseEntity<Turma> saveTurma(@RequestBody TurmaDTO turmaDTO) {
    Turma savedTurma = turmaService.saveTurma(turmaDTO);
    return ResponseEntity.ok(savedTurma);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Turma> getTurmaById(@PathVariable long id) {
    Turma turma = turmaService.getTurma(id);
    return ResponseEntity.ok(turma);
  }

  @GetMapping("/turmas")
  public List<Turma> getAllTurmas() {
    return turmaService.getTurmas();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Turma> updateTurma(@PathVariable long id, @RequestBody TurmaDTO turmaDTO){
    Turma updatedTurma = turmaService.updateTurma(id, turmaDTO);
    return ResponseEntity.ok(updatedTurma);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTurma(@PathVariable long id){
    turmaService.deleteTurma(id);
    return ResponseEntity.noContent().build();
  }

}
