package com.multiversa.escola.service;

import com.multiversa.escola.exception.IdNaoEncontradoException;
import com.multiversa.escola.model.Disciplina;
import com.multiversa.escola.model.Professor;
import com.multiversa.escola.model.Turma;
import com.multiversa.escola.repository.DisciplinaRepository;
import com.multiversa.escola.repository.NotaRepository;
import com.multiversa.escola.repository.ProfessorRepository;
import com.multiversa.escola.repository.TurmaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService {

  @Autowired
  private DisciplinaRepository disciplinaRepository;
  @Autowired
  private ProfessorRepository professorRepository;
  @Autowired
  private TurmaRepository turmaRepository;
  @Autowired
  private NotaRepository notaRepository;

  public Disciplina saveDisciplina(Disciplina disciplina, Long professorId) {
    Professor professor = professorRepository.findById(professorId)
        .orElseThrow(() -> new RuntimeException("Professor não encontrado"));

    disciplina.setProfessor(professor);
    return disciplinaRepository.save(disciplina);
  }
  public Disciplina getDisciplina(long disciplinaId) {
    return disciplinaRepository.findById(disciplinaId)
        .orElseThrow(() -> new IdNaoEncontradoException("Disciplina com ID " + disciplinaId + " não foi encontrada."));
  }

  public List<Disciplina> getDisciplinas() {
    return disciplinaRepository.findAll();
  }

  public Disciplina updateDisciplina(long disciplinaId, Disciplina disciplina) {
    Disciplina existingDisciplina = disciplinaRepository.findById(disciplinaId)
        .orElseThrow(() -> new IdNaoEncontradoException("Disciplina com ID " + disciplinaId + " não foi encontrada."));
    // Verifique se o professor existe
    if (disciplina.getProfessor() != null && disciplina.getProfessor().getId() != null) {
      Professor professor = professorRepository.findById(disciplina.getProfessor().getId())
          .orElseThrow(() -> new RuntimeException("Professor não encontrado"));
      existingDisciplina.setProfessor(professor);
    }
    existingDisciplina.setNome(disciplina.getNome());
    existingDisciplina.setCargaHoraria(disciplina.getCargaHoraria());
    return disciplinaRepository.save(existingDisciplina);
  }

  public void deleteDisciplina(long disciplinaId) {
    Disciplina existingDisciplina = disciplinaRepository.findById(disciplinaId)
        .orElseThrow(() -> new IdNaoEncontradoException("Disciplina com ID " + disciplinaId + " não foi encontrada."));
    // Remove todas as notas associadas à disciplina
    notaRepository.deleteAll(existingDisciplina.getNotas());
    // Remove a disciplina de todas as turmas que a contêm
    List<Turma> turmas = turmaRepository.findAll();
    for (Turma turma : turmas) {
      turma.getDisciplinas().remove(existingDisciplina);
      turmaRepository.save(turma);
    }
    disciplinaRepository.delete(existingDisciplina);
  }

}
