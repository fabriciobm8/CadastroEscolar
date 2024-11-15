package com.multiversa.escola.service;

import com.multiversa.escola.model.Disciplina;
import com.multiversa.escola.model.Professor;
import com.multiversa.escola.repository.DisciplinaRepository;
import com.multiversa.escola.repository.ProfessorRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

  @Autowired
  private DisciplinaRepository disciplinaRepository;

  @Autowired
  private ProfessorRepository professorRepository;

  public Disciplina saveDisciplina(Disciplina disciplina, Long professorId) {
    Professor professor = professorRepository.findById(professorId)
        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

    disciplina.setProfessor(professor);
    return disciplinaRepository.save(disciplina);
  }
  public Disciplina getDisciplina(long disciplinaId) {
    return disciplinaRepository.findById(disciplinaId).orElseThrow(() -> new
        RuntimeException("Disciplina nao encontrada"));
  }

  public List<Disciplina> getDisciplinas() {
    return disciplinaRepository.findAll();
  }

  public Disciplina updateDisciplina (long disciplinaId, Disciplina disciplina) {
    Disciplina existingDisciplina = disciplinaRepository.findById(disciplinaId).orElseThrow(() -> new
        RuntimeException("Disciplina não encontrada"));
    existingDisciplina.setNome(disciplina.getNome());
    existingDisciplina.setCargaHoraria(disciplina.getCargaHoraria());
    existingDisciplina.setProfessor(disciplina.getProfessor());
    disciplinaRepository.save(existingDisciplina);
    return existingDisciplina;
  }

  public Disciplina deleteDisciplina(long disciplinaId) {
    Disciplina existingDisciplina = disciplinaRepository.findById(disciplinaId).orElseThrow(() -> new
        RuntimeException("Disciplina não encontrada"));
    disciplinaRepository.deleteById(disciplinaId);
    return existingDisciplina;
  }

}
