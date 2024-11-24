package com.multiversa.escola.service;

import com.multiversa.escola.exception.IdNaoEncontradoException;
import com.multiversa.escola.model.Disciplina;
import com.multiversa.escola.model.Professor;
import com.multiversa.escola.repository.DisciplinaRepository;
import com.multiversa.escola.repository.ProfessorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

  @Autowired
  ProfessorRepository professorRepository;
  @Autowired
  DisciplinaService disciplinaService;

  public Professor saveProfessor (Professor professor) {
    return professorRepository.save(professor);
  }

  public Professor getProfessor(long professorId) {
    return professorRepository.findById(professorId)
        .orElseThrow(() -> new IdNaoEncontradoException("Professor com ID " + professorId + " não foi encontrado."));
  }

  public List<Professor> getProfessors() {
    return professorRepository.findAll();
  }

  public Professor updateProfessor(long professorId, Professor professor) {
    Professor existingProfessor = professorRepository.findById(professorId)
        .orElseThrow(() -> new IdNaoEncontradoException("Professor com ID " + professorId + " não foi encontrado."));
    existingProfessor.setNome(professor.getNome());
    existingProfessor.setEmail(professor.getEmail());
    existingProfessor.setDisciplinaPrincipal(professor.getDisciplinaPrincipal());
    professorRepository.save(existingProfessor);
    return existingProfessor;
  }

  public void deleteProfessor(long professorId) {
    Professor existingProfessor = professorRepository.findById(professorId)
        .orElseThrow(() -> new IdNaoEncontradoException("Professor com ID " + professorId + " não foi encontrado."));
    // Remove todas as disciplinas associadas ao professor
    for (Disciplina disciplina : existingProfessor.getDisciplinas()) {
      disciplinaService.deleteDisciplina(disciplina.getId());
    }
    professorRepository.delete(existingProfessor);
  }

}
