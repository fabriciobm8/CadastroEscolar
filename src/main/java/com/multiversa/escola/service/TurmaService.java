package com.multiversa.escola.service;

import com.multiversa.escola.exception.IdNaoEncontradoException;
import com.multiversa.escola.exception.ListaVaziaException;
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
    return turmaRepository.findById(turmaId)
        .orElseThrow(() -> new IdNaoEncontradoException("Turma com ID " + turmaId + " não foi encontrada."));
  }

  public List<Turma> getTurmas() {
    List<Turma> turmas = turmaRepository.findAll();
    if (turmas.isEmpty()) {
      throw new ListaVaziaException("Não existem turmas cadastradas no sistema.");
    }
    return turmas;
  }

  public Turma updateTurma(long turmaId, TurmaDTO turmaDTO) {
    Turma existingTurma = turmaRepository.findById(turmaId)
        .orElseThrow(() -> new IdNaoEncontradoException("Turma com ID " + turmaId + " não foi encontrada."));
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

  public void deleteTurma(long turmaId) {
    Turma existingTurma = turmaRepository.findById(turmaId)
        .orElseThrow(() -> new IdNaoEncontradoException("Turma com ID " + turmaId + " não foi encontrada."));
    // Remove as referências dos alunos à turma
    for (Aluno aluno : existingTurma.getAlunos()) {
      aluno.setTurma(null);
      alunoRepository.save(aluno);
    }
    // Remove as referências das disciplinas
    existingTurma.getDisciplinas().clear();
    turmaRepository.delete(existingTurma);
  }

}
