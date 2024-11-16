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

  public Turma updateTurma(long turmaId, TurmaDTO turmaDTO) {
    Turma existingTurma = turmaRepository.findById(turmaId)
        .orElseThrow(() -> new RuntimeException("Turma não encontrada"));
    // Atualiza os campos básicos
    existingTurma.setNome(turmaDTO.getNome());
    existingTurma.setAno(turmaDTO.getAno());
    // Atualiza os alunos
    List<Aluno> alunos = alunoRepository.findAllById(turmaDTO.getAlunoIds());
    for (Aluno aluno : alunos) {
      aluno.setTurma(existingTurma); // Define a turma para cada aluno
    }
    existingTurma.setAlunos(alunos); // Atualiza a lista de alunos
    // Atualiza as disciplinas
    List<Disciplina> disciplinas = disciplinaRepository.findAllById(turmaDTO.getDisciplinaIds());
    existingTurma.setDisciplinas(disciplinas); // Atualiza a lista de disciplinas
    return turmaRepository.save(existingTurma); // Salva as alterações
  }

  public Turma deleteTurma(long turmaId) {
    Turma existingTurma = turmaRepository.findById(turmaId).orElseThrow(() -> new
        RuntimeException("Turma não encontrada"));
    turmaRepository.delete(existingTurma);
    return existingTurma;
  }

}
