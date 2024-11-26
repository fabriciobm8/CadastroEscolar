package com.multiversa.escola.service;

import com.multiversa.escola.exception.AlunoExistenteNaTurmaException;
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

  private void validateAlunosNotInOtherTurma(List<Aluno> alunos) {
    for (Aluno aluno : alunos) {
      if (aluno.getTurma() != null) {
        throw new AlunoExistenteNaTurmaException(
            String.format("O aluno %s (ID: %d) já está matriculado na %s",
                aluno.getNome(),
                aluno.getId(),
                aluno.getTurma().getNome())
        );
      }
    }
  }

  public Turma saveTurma(TurmaDTO turmaDTO) {
    List<Aluno> alunos = alunoRepository.findAllById(turmaDTO.getAlunoIds());
    if (alunos.size() != turmaDTO.getAlunoIds().size()) {
      throw new IdNaoEncontradoException("Um ou mais alunos não foram encontrados.");
    }
    validateAlunosNotInOtherTurma(alunos);
    Turma turma = new Turma();
    turma.setNome(turmaDTO.getNome());
    turma.setAno(turmaDTO.getAno());
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
    List<Aluno> newAlunos = alunoRepository.findAllById(turmaDTO.getAlunoIds());
    if (newAlunos.size() != turmaDTO.getAlunoIds().size()) {
      throw new IdNaoEncontradoException("Um ou mais alunos não foram encontrados.");
    }
    // Remove os alunos que não estão mais na turma
    for (Aluno currentAluno : existingTurma.getAlunos()) {
      if (!turmaDTO.getAlunoIds().contains(currentAluno.getId())) {
        currentAluno.setTurma(null);
        alunoRepository.save(currentAluno);
      }
    }
    // Valida e adiciona novos alunos
    for (Aluno newAluno : newAlunos) {
      if (newAluno.getTurma() != null && !newAluno.getTurma().getId().equals(turmaId)) {
        throw new AlunoExistenteNaTurmaException(
            String.format("O aluno %s (ID: %d) já está matriculado na %s",
                newAluno.getNome(),
                newAluno.getId(),
                newAluno.getTurma().getNome())
        );
      }
    }
    existingTurma.setNome(turmaDTO.getNome());
    existingTurma.setAno(turmaDTO.getAno());
    for (Aluno aluno : newAlunos) {
      aluno.setTurma(existingTurma);
    }
    existingTurma.setAlunos(newAlunos);
    List<Disciplina> disciplinas = disciplinaRepository.findAllById(turmaDTO.getDisciplinaIds());
    existingTurma.setDisciplinas(disciplinas);
    return turmaRepository.save(existingTurma);
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
