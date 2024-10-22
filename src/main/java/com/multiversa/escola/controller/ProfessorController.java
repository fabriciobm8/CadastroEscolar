package com.multiversa.escola.controller;

import com.multiversa.escola.model.Professor;
import com.multiversa.escola.service.ProfessorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
public class ProfessorController {

  @Autowired
  ProfessorService professorService;

  @PostMapping
  public ResponseEntity<Professor> saveProfessor(@RequestBody Professor professor) {
    Professor savedProfessor = professorService.saveProfessor(professor);
    return ResponseEntity.ok(savedProfessor);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Professor> getProfessorById (@PathVariable long id) {
    Professor professor = professorService.getProfessor(id);
    return ResponseEntity.ok(professor);
  }

  @GetMapping("/professors")
  public List<Professor> getAllProfessors(){
    return professorService.getProfessors();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Professor> updateProfessor(@PathVariable long id, @RequestBody Professor professor){
    Professor updatedProfessor = professorService.updateProfessor(id, professor);
    return ResponseEntity.ok(updatedProfessor);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Professor> deleteProfessor(@PathVariable long id) {
    professorService.deleteProfessor(id);
    return ResponseEntity.noContent().build();
  }

}
