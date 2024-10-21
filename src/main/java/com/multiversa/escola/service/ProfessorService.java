package com.multiversa.escola.service;

import com.multiversa.escola.model.Professor;
import com.multiversa.escola.repository.ProfessorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

  @Autowired
  ProfessorRepository professorRepository;

  public Professor saveProfessor (Professor professor) {
    return professorRepository.save(professor);
  }

  public Professor getProfessor(long professorId) {
    return professorRepository.findById(professorId).orElseThrow(() -> new
        RuntimeException("Professor não encontrado"));
  }

  public List<Professor> getProfessors() {
    return professorRepository.findAll();
  }

  public Professor updateProfessor(long professorId, Professor professor) {
    Professor existingProfessor = professorRepository.findById(professorId).orElseThrow(() -> new
        RuntimeException("Professor não encontrado"));
    existingProfessor.setNome(professor.getNome());
    existingProfessor.setEmail(professor.getEmail());
    existingProfessor.setDisciplinaPrincipal(professor.getDisciplinaPrincipal());
    existingProfessor.setDisciplinas(professor.getDisciplinas());
    existingProfessor.setTurmas(professor.getTurmas());
    professorRepository.save(existingProfessor);
    return existingProfessor;
  }

  public Professor deleteProfessor(long professorId) {
    Professor existingProfessor = professorRepository.findById(professorId).orElseThrow(() -> new
        RuntimeException("Professor não encontrado"));
    professorRepository.deleteById(professorId);
    return existingProfessor;
  }

}
