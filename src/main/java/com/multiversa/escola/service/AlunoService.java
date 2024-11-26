package com.multiversa.escola.service;

import com.multiversa.escola.exception.EmailJaCadastradoException;
import com.multiversa.escola.exception.IdNaoEncontradoException;
import com.multiversa.escola.exception.ListaVaziaException;
import com.multiversa.escola.model.Aluno;
import com.multiversa.escola.model.Turma;
import com.multiversa.escola.repository.AlunoRepository;
import com.multiversa.escola.repository.NotaRepository;
import com.multiversa.escola.repository.TurmaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

  @Autowired
  private AlunoRepository alunoRepository;
  @Autowired
  private NotaRepository notaRepository;

  @Autowired
  private TurmaRepository turmaRepository;

  public Aluno saveAluno(Aluno aluno) {
    if (alunoRepository.existsByEmail(aluno.getEmail())) {
      throw new EmailJaCadastradoException("E-mail já cadastrado no sistema");
    }
    return alunoRepository.save(aluno);
  }

  public Aluno getAluno(long alunoId) {
    return alunoRepository.findById(alunoId)
        .orElseThrow(() -> new IdNaoEncontradoException("Aluno com ID " + alunoId + " não foi encontrado."));
  }

  public Aluno getEmail(String email) {
    return alunoRepository.findByEmail(email)
        .orElseThrow(() -> new IdNaoEncontradoException("Aluno com email " + email + " não foi encontrado."));
  }

  public Aluno getMatricula(String matricula) {
    return alunoRepository.findByMatricula(matricula)
        .orElseThrow(() -> new IdNaoEncontradoException("Aluno com matrícula " + matricula + " não foi encontrado."));
  }

  public List<Aluno> getAlunos() {
    List<Aluno> alunos = alunoRepository.findAll();
    if (alunos.isEmpty()) {
      throw new ListaVaziaException("Não existem alunos cadastrados no sistema.");
    }
    return alunos;
  }

  public Aluno updateAluno(long alunoId, Aluno aluno) {
    Aluno existingAluno = alunoRepository.findById(alunoId)
        .orElseThrow(() -> new IdNaoEncontradoException("Aluno com ID " + alunoId + " não foi encontrado."));
    if (alunoRepository.existsByEmailAndIdNot(aluno.getEmail(), alunoId)) {
      throw new EmailJaCadastradoException("E-mail já cadastrado no sistema");
    }
    existingAluno.setNome(aluno.getNome());
    existingAluno.setMatricula(aluno.getMatricula());
    existingAluno.setEmail(aluno.getEmail());
    existingAluno.setDataNascimento(aluno.getDataNascimento());
    alunoRepository.save(existingAluno);
    return existingAluno;
  }

  public void deleteAluno(long alunoId) {
    Aluno existingAluno = alunoRepository.findById(alunoId)
        .orElseThrow(() -> new IdNaoEncontradoException("Aluno com ID " + alunoId + " não foi encontrado."));

    // Remove aluno de turma associada
    if (existingAluno.getTurma() != null) {
      Turma turma = existingAluno.getTurma();
      turma.getAlunos().remove(existingAluno); // Remove o aluno da turma
      existingAluno.setTurma(null); // Remove a referência do aluno à turma
      turmaRepository.save(turma); // Salva a turma com a referência removida
    }
    alunoRepository.delete(existingAluno);
  }

}
