package com.multiversa.escola.service;

import com.multiversa.escola.model.Aluno;
import com.multiversa.escola.model.Disciplina;
import com.multiversa.escola.model.Turma;
import com.multiversa.escola.model.TurmaDTO;
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
  @Autowired
  AlunoRepository alunoRepository;
  @Autowired
  DisciplinaRepository disciplinaRepository;

  public Turma saveTurma(TurmaDTO turmaDTO) {
    Turma turma = new Turma();
    turma.setNome(turmaDTO.getNome());
    turma.setAno(turmaDTO.getAno());
    List<Aluno> alunos = alunoRepository.findAllById(turmaDTO.getAlunoIds());
    for (Aluno aluno : alunos) {
      aluno.setTurma(turma);
    }
    turma.setAlunos(alunos);
    List<Disciplina> disciplinas = disciplinaRepository.findAllById(turmaDTO.getDisciplinaIds());
    turma.setDisciplinas(disciplinas);
    return turmaRepository.save(turma);
  }

  public Turma getTurma(long turmaId) {
    return turmaRepository.findById(turmaId).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
  }

  public List<Turma> getTurmas() {
    return turmaRepository.findAll();
  }

  public Turma updateTurma(long turmaId, Turma turma) {
    Turma existingTurma = turmaRepository.findById(turmaId).orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    existingTurma.setNome(turma.getNome());
    existingTurma.setAno(turma.getAno());
    for (Aluno aluno : turma.getAlunos()) {
      aluno.setTurma(existingTurma);
    }
    existingTurma.setAlunos(turma.getAlunos());
    existingTurma.setDisciplinas(turma.getDisciplinas());
    return turmaRepository.save(existingTurma);
  }

  public Turma deleteTurma(long turmaId) {
    Turma existingTurma = turmaRepository.findById(turmaId).orElseThrow(() -> new
        RuntimeException("Turma não encontrada"));
    turmaRepository.deleteById(turmaId);
    return existingTurma;
  }

}
