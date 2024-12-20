package com.multiversa.escola.controller;

import com.multiversa.escola.model.Aluno;
import com.multiversa.escola.service.AlunoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

  @Autowired
  private AlunoService alunoService;

  @PostMapping
  public ResponseEntity<Aluno> saveAluno(@Valid @RequestBody Aluno aluno) {
    Aluno savedAluno = alunoService.saveAluno(aluno);
    return ResponseEntity.ok(savedAluno);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Aluno> getAlunoById(@PathVariable long id) {
    Aluno aluno = alunoService.getAluno(id);
    return ResponseEntity.ok(aluno);
  }

  @GetMapping("/email/{email}")
  public ResponseEntity<Aluno> getAlunoByEmail(@PathVariable String email) {
    Aluno aluno = alunoService.getEmail(email);
    return ResponseEntity.ok(aluno);
  }

  @GetMapping("/matricula/{matricula}")
  public ResponseEntity<Aluno> getAlunoByMatricula(@PathVariable String matricula) {
    Aluno aluno = alunoService.getMatricula(matricula);
    return ResponseEntity.ok(aluno);
  }

  @GetMapping("/alunos")
  public List<Aluno> getAllAlunos() {
    return alunoService.getAlunos();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Aluno> updateAluno (@PathVariable long id, @Valid @RequestBody Aluno aluno) {
    Aluno updatedAluno = alunoService.updateAluno(id, aluno);
    return ResponseEntity.ok(updatedAluno);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAluno(@PathVariable long id) {
    alunoService.deleteAluno(id);
    return ResponseEntity.noContent().build();
  }

}
