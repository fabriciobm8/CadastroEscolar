package com.multiversa.escola.service;

import com.multiversa.escola.model.Turma;
import com.multiversa.escola.repository.AlunoRepository;
import com.multiversa.escola.repository.DisciplinaRepository;
import com.multiversa.escola.repository.TurmaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurmaService {

  @Autowired
  TurmaRepository turmaRepository;

  public Turma saveTurma(Turma turma) {
    return turmaRepository.save(turma);
  }

  public Turma getTurma(long turmaId) {
    return turmaRepository.findById(turmaId).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
  }

  public List<Turma> getTurmas() {
    return turmaRepository.findAll();
  }

  public Turma updateTurma(long turmaId, Turma turma) {
    Turma existingTurma = turmaRepository.findById(turmaId).orElseThrow(() -> new
        RuntimeException("Turma não encontrada"));
    existingTurma.setNome(turma.getNome());
    existingTurma.setAno(turma.getAno());
    existingTurma.setAlunos(turma.getAlunos());
    existingTurma.setDisciplinas(turma.getDisciplinas());
    turmaRepository.save(existingTurma);
    return existingTurma;
  }

  public Turma deleteTurma(long turmaId) {
    Turma existingTurma = turmaRepository.findById(turmaId).orElseThrow(() -> new
        RuntimeException("Turma não encontrada"));
    turmaRepository.deleteById(turmaId);
    return existingTurma;
  }

}
