package com.multiversa.escola.controller;

import com.multiversa.escola.model.Disciplina;
import com.multiversa.escola.service.DisciplinaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplina")
public class DisciplinaController {

  @Autowired
  DisciplinaService disciplinaService;

  @PostMapping
  public ResponseEntity<Disciplina> saveDisciplina(
      @Valid @RequestBody Disciplina disciplina,
      @RequestParam Long professorId) {

    Disciplina savedDisciplina = disciplinaService.saveDisciplina(disciplina, professorId);
    return ResponseEntity.ok(savedDisciplina);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Disciplina> getDisciplinaById(@PathVariable long id) {
    Disciplina disciplina = disciplinaService.getDisciplina(id);
    return ResponseEntity.ok(disciplina);
  }

  @GetMapping("/disciplinas")
  public List<Disciplina> getAllDisciplinas(){
    return disciplinaService.getDisciplinas();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Disciplina> updateDisciplina(@PathVariable long id, @Valid @RequestBody Disciplina disciplina){
    Disciplina updateDisciplina = disciplinaService.updateDisciplina(id, disciplina);
    return ResponseEntity.ok(updateDisciplina);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDisciplina(@PathVariable long id){
    disciplinaService.deleteDisciplina(id);
    return ResponseEntity.noContent().build();
  }

}
