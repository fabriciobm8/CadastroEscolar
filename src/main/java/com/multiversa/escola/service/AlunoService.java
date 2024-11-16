package com.multiversa.escola.service;

import com.multiversa.escola.model.Aluno;
import com.multiversa.escola.repository.AlunoRepository;
import com.multiversa.escola.repository.NotaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

  @Autowired
  private AlunoRepository alunoRepository;
  @Autowired
  private NotaRepository notaRepository;

  public Aluno saveAluno(Aluno aluno) {
    return alunoRepository.save(aluno);
  }

  public Aluno getAluno(long alunoId) {
    return alunoRepository.findById(alunoId).orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
  }

  public List<Aluno> getAlunos() {
    return alunoRepository.findAll();
  }

  public Aluno updateAluno(long alunoId, Aluno aluno) {
    Aluno existingAluno = alunoRepository.findById(alunoId).orElseThrow(() -> new
        RuntimeException("Aluno não encontrado"));
    existingAluno.setNome(aluno.getNome());
    existingAluno.setMatricula(aluno.getMatricula());
    existingAluno.setEmail(aluno.getEmail());
    existingAluno.setDataNascimento(aluno.getDataNascimento());
    alunoRepository.save(existingAluno);
    return existingAluno;
  }

  public void deleteAluno(long alunoId) {
    Aluno existingAluno = alunoRepository.findById(alunoId)
        .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    // Remove todas as notas associadas ao aluno
    notaRepository.deleteAll(existingAluno.getNotas());
    // Remove a referência da turma
    if (existingAluno.getTurma() != null) {
      existingAluno.setTurma(null);
      alunoRepository.save(existingAluno);
    }
    alunoRepository.delete(existingAluno);
  }

}
